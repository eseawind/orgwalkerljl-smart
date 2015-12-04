
package org.walkerljl.smart.dao.rw;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;

/**
 * 
 * 基于Spring读写分离
 *
 * @author lijunlin
 */
abstract class AbstractRoutingDataSource extends AbstractDataSource implements InitializingBean {
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private Object masterDataSource;
	private DataSource resolvedMasterDataSource;// 主库
	private List<Object> slaveDataSoures;
	private List<DataSource> resolvedSlaveDataSources;// 从库

	private int readDsSize;// read-only data source的数量,做负载均衡的时候需要
	private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

	public void setSlaveDataSoures(List<Object> slaveDataSoures) {
		this.slaveDataSoures = slaveDataSoures;
	}

	public void setMasterDataSource(Object masterDataSource) {
		this.masterDataSource = masterDataSource;
	}

	public int getReadDsSize() {
		return readDsSize;
	}

	public Connection getConnection() throws SQLException {
		return determineTargetDataSource().getConnection();
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return determineTargetDataSource().getConnection(username, password);
	}

	public void afterPropertiesSet() throws Exception {
		this.resolvedMasterDataSource = resolveSpecifiedDataSource(masterDataSource);
		if (this.resolvedMasterDataSource == null) {
			throw new IllegalArgumentException("Property 'masterDataSource' is required");
		}
		if (slaveDataSoures == null || slaveDataSoures.size() <= 0) {
			return;
		}
		resolvedSlaveDataSources = new ArrayList<DataSource>();
		for (Object item : slaveDataSoures) {
			DataSource ds = resolveSpecifiedDataSource(item);
			if (ds != null) {
				resolvedSlaveDataSources.add(ds);
			}
		}
		readDsSize = resolvedSlaveDataSources.size();
	}

	/**
	 * 确认目标数据源
	 * 
	 * @return
	 */
	protected DataSource determineTargetDataSource() {
		if (!determineCurrentLookupKey() || readDsSize <= 0) {
			return getResolvedMasterDataSource();// 写操作或者没有配置读库的时候
		} else {
			return loadBalance();
		}
	}

	protected List<DataSource> getResolvedSlaveDataSources() {
		return resolvedSlaveDataSources;
	}

	protected DataSource getResolvedMasterDataSource() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Using Master DataSource [%s]", resolvedMasterDataSource.toString()));
		}
		return resolvedMasterDataSource;
	}

	/**
	 * 获取真实的data source
	 * 
	 * @param dataSource(jndi | real data source)
	 * @return
	 * @throws IllegalArgumentException
	 */
	private DataSource resolveSpecifiedDataSource(Object dataSource)
			throws IllegalArgumentException {
		DataSource ds = null;
		if (dataSource instanceof DataSource) {
			ds = (DataSource) dataSource;
		} else if (dataSource instanceof String) {
			ds = this.dataSourceLookup.getDataSource((String) dataSource);
		} else {
			throw new IllegalArgumentException("Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
		}
		try {
			ds.getConnection().close();// 测试数据源连接是否有效
		} catch (Exception e) {
			LOGGER.warn("Invalid DataSource:" + dataSource.toString(), e);
			return null;
		}
		return ds;
	}

	protected abstract boolean determineCurrentLookupKey();

	protected abstract DataSource loadBalance();
}