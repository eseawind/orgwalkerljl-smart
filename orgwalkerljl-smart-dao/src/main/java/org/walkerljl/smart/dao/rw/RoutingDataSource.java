package org.walkerljl.smart.dao.rw;

import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.springframework.core.NamedThreadLocal;

/**
 * 
 * 读写分离的数据源
 *
 * @author lijunlin
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
	private final static NamedThreadLocal<Boolean> keys = new NamedThreadLocal<Boolean>("RoutingDataSource");
	private AtomicInteger count = new AtomicInteger(0);

	public static void readOnly() {
		keys.set(true);
	}

	public static void clear() {
		keys.remove();
	}

	@Override
	protected boolean determineCurrentLookupKey() {
		Boolean readyonly = keys.get();
		return readyonly != null && readyonly.booleanValue();
	}

	@Override
	protected DataSource loadBalance() {
		int index = Math.abs(count.incrementAndGet()) % getReadDsSize();
		DataSource dataSource = getResolvedSlaveDataSources().get(index);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Using Master DataSource [%s]", dataSource.toString()));
		}
		return dataSource;
	}
}
