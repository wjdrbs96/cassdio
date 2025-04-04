package kr.hakdang.cassdio.core.domain.cluster;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.metadata.Node;
import kr.hakdang.cassdio.IntegrationTest;
import kr.hakdang.cassdio.core.domain.cluster.ClusterException.ClusterNodeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * ClusterNodeGetCommanderTest
 *
 * @author seungh0
 * @since 2024-07-03
 */
class ClusterNodeGetCommanderTest extends IntegrationTest {

    @Autowired
    private ClusterNodeGetCommander clusterNodeGetCommander;

    @Test
    void not_exists_node_in_cluster() {
        // when & then
        assertThatThrownBy(() -> clusterNodeGetCommander.getNode(makeSession(), UUID.randomUUID()))
            .isInstanceOf(ClusterNodeNotFoundException.class);
    }

    @Test
    void get_node_in_cluster() {
        // given
        try (CqlSession session = makeSession()) {

            Map<UUID, Node> nodes = session.getMetadata().getNodes();

            for (Map.Entry<UUID, Node> node : nodes.entrySet()) {
                ClusterNode sut = clusterNodeGetCommander.getNode(session, node.getKey());

                assertThat(sut.getNodeId()).isEqualTo(node.getValue().getHostId());
                assertThat(sut.getDatacenter()).isEqualTo("dc1");
                assertThat(sut.getRack()).isEqualTo("rack1");
                assertThat(sut.getUpSinceMillis()).isGreaterThan(0);
                assertThat(sut.getCassandraVersion()).isNotNull();
                assertThat(sut.getCassandraVersion()).isEqualTo(String.valueOf(node.getValue().getCassandraVersion()));
            }
        }
    }

}
