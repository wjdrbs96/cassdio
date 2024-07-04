package kr.hakdang.cadio.web.route.cluster.table;

import jakarta.validation.Valid;
import kr.hakdang.cadio.core.domain.cluster.keyspace.table.ClusterTable;
import kr.hakdang.cadio.core.domain.cluster.keyspace.table.ClusterTableGetResult;
import kr.hakdang.cadio.web.common.dto.request.CursorRequest;
import kr.hakdang.cadio.web.common.dto.response.ApiResponse;
import kr.hakdang.cadio.web.common.dto.response.ItemListWithCursorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClusterTableApi
 *
 * @author seungh0
 * @since 2024-07-01
 */
@RestController
@RequestMapping("/api/cassandra/cluster/{clusterId}/keyspace/{keyspace}")
public class ClusterTableApi {

    private final ClusterTableReader clusterTableReader;

    public ClusterTableApi(
        ClusterTableReader clusterTableReader
    ) {
        this.clusterTableReader = clusterTableReader;
    }

    @GetMapping("/table")
    public ApiResponse<ItemListWithCursorResponse<ClusterTable, String>> listTables(
        @PathVariable String clusterId,
        @PathVariable String keyspace,
        @Valid CursorRequest cursorRequest
    ) {
        ItemListWithCursorResponse<ClusterTable, String> tables = clusterTableReader.listTables(clusterId, keyspace, cursorRequest);
        return ApiResponse.ok(tables);
    }

    @GetMapping("/table/{table}")
    public ApiResponse<ClusterTableGetResult> getTable(
        @PathVariable String clusterId,
        @PathVariable String keyspace,
        @PathVariable String table,
        @RequestParam(required = false, defaultValue = "false") boolean withTableDescribe
    ) {
        ClusterTableGetResult cluster = clusterTableReader.getTable(clusterId, keyspace, table, withTableDescribe);
        return ApiResponse.ok(cluster);
    }

}
