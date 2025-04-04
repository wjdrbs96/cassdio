package kr.hakdang.cassdio.core.domain.cluster.keyspace.table;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.protocol.internal.util.Bytes;
import kr.hakdang.cassdio.core.domain.cluster.BaseClusterCommander;
import kr.hakdang.cassdio.core.domain.cluster.CqlSessionSelectResults;
import kr.hakdang.cassdio.core.domain.cluster.keyspace.CassdioColumnDefinition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * ClusterTableRowCommander
 *
 * @author akageun
 * @since 2024-07-25
 */
@Slf4j
@Service
public class ClusterTableRowCommander extends BaseClusterCommander {

    public CqlSessionSelectResults rowSelect(CqlSession session, TableDTO.ClusterTableRowArgs args) {
        SimpleStatement statement = QueryBuilder.selectFrom(args.getKeyspace(), args.getTable())
            .all()
            .build()
            .setPageSize(args.getPageSize())
            .setTimeout(Duration.ofSeconds(3))  // 3s timeout
            .setPagingState(StringUtils.isNotBlank(args.getCursor()) ? Bytes.fromHexString(args.getCursor()) : null);

        ResultSet resultSet = session.execute(statement);

        return CqlSessionSelectResults.of(
            convertRows(session, resultSet),
            CassdioColumnDefinition.makes(resultSet.getColumnDefinitions()),
            resultSet.getExecutionInfo().getPagingState()
        );
    }
}
