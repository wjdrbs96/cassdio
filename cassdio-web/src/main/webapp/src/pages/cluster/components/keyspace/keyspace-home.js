import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Spinner from "../../../../components/spinner";
import KeyspaceTableList from "./keyspace-table-list";
import useCassdio from "../../../../commons/hooks/useCassdio";
import {Tooltip} from "react-bootstrap";
import {CassdioUtils} from "../../../../utils/cassdioUtils";

const KeyspaceHome = () => {

    const routeParams = useParams();
    const {errorCatch} = useCassdio();
    //const {doGetKeyspaceList} = useCluster();

    const [detailLoading, setDetailLoading] = useState(false);
    const [keyspaceDescribe, setKeyspaceDescribe] = useState('');
    const [keyspaceDetail, setKeyspaceDetail] = useState({
        columns: [],
        row: {}
    });
    const [tableLoading, setTableLoading] = useState(false);
    const [tableCursor, setTableCursor] = useState(null)
    const [tableList, setTableList] = useState([]);

    useEffect(() => {
        //show component
        setKeyspaceDescribe('');
        setDetailLoading(true)
        setTableList([]);
        axios({
            method: "GET",
            url: `/api/cassandra/cluster/${routeParams.clusterId}/keyspace/${routeParams.keyspaceName}`,
            params: {
                withTableList: true,
            }
        }).then((response) => {
            console.log("res ", response);
            setKeyspaceDescribe(response.data.result.describe)
            setKeyspaceDetail(response.data.result.detail);

            setTableList(response.data.result.tableList.rows)
            if (response.data.result.tableList.nextCursor) {
                setTableCursor(response.data.result.tableList.nextCursor)
            }

        }).catch((error) => {
            errorCatch(error)
        }).finally(() => {
            setDetailLoading(false)
        });
        //
        // axios({
        //     method: "GET",
        //     url: `/api/cassandra/cluster/${routeParams.clusterId}/keyspace/${routeParams.keyspaceName}/table`,
        //     params: {
        //         size: 50,
        //         cursor: tableCursor // TODO: 스크롤 페이지네이션 처리
        //     }
        // }).then((response) => {
        //     console.log("KeyspaceHome ", response);
        //     setTableList(response.data.result.items)
        //     if (response.data.result.cursor.hasNext) {
        //         setTableCursor(response.data.result.cursor.next)
        //     }
        // }).catch((error) => {
        //     axiosCatch(error)
        // }).finally(() => {
        //     setTableLoading(false)
        // });

        return () => {
            //hide component

        };
    }, [routeParams.clusterId, routeParams.keyspaceName]);

    return (
        <>
            <div className={"row pt-3"}>
                <nav className={"breadcrumb-arrow"} aria-label="breadcrumb">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <Link to={`/cluster/${routeParams.clusterId}`}
                                  className={"link-body-emphasis text-decoration-none"}>
                                Cluster
                            </Link>
                        </li>
                        <li className="breadcrumb-item active" aria-current="page">
                            {routeParams.keyspaceName}
                        </li>
                    </ol>
                </nav>
            </div>

            <div
                className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2 className="h2">
                    Keyspace
                </h2>
                {/*<div className="btn-toolbar mb-2 mb-md-0">*/}
                {/*    <div className="btn-group me-2">*/}
                {/*        <button type="button" className="btn btn-sm btn-outline-secondary">Share</button>*/}
                {/*        <button type="button" className="btn btn-sm btn-outline-secondary">Export</button>*/}
                {/*    </div>*/}
                {/*    <button type="button"*/}
                {/*            className="btn btn-sm btn-outline-secondary dropdown-toggle d-flex align-items-center gap-1">*/}
                {/*        This week*/}
                {/*    </button>*/}
                {/*</div>*/}
            </div>

            <Spinner loading={detailLoading}>

                {
                    keyspaceDetail && <>
                        <div className="table-responsive small">
                            <table
                                className="table table-sm table-fixed table-lock-height table-hover">
                                <tbody className="table-group-divider"
                                       style={{maxHeight: "100vh"}}>

                                {
                                    keyspaceDetail.columns.map((info, infoIndex) => {
                                        return (
                                            <tr key={`resultBody${infoIndex}`}>
                                                <th className={"text-center text-break"}>
                                                    {info.columnName}
                                                </th>
                                                <td className={"text-center text-break"}
                                                    key={`resultItem${infoIndex}`}>
                                                    {
                                                        CassdioUtils.renderData(keyspaceDetail.row[info.columnName])
                                                    }
                                                </td>
                                            </tr>
                                        )
                                    })
                                }
                                </tbody>
                            </table>
                        </div>

                    </>
                }

                {
                    keyspaceDescribe && <>
                        <div className={"row mb-3"}>
                            <h3 className={"h3"}>Describe</h3>
                            <div className={"col"}>
                                <code className={"text-break"}>
                                    {keyspaceDescribe}
                                </code>
                            </div>
                        </div>
                    </>
                }

                <div className={"row mt-3"}>
                    <div className={"col-md-6 col-sm-12"}>
                        <h2 className="h3">Tables</h2>

                        <KeyspaceTableList clusterId={routeParams.clusterId}
                                           keyspaceName={routeParams.keyspaceName}
                                           tableList={tableList}/>

                    </div>
                    <div className={"col-md-6 col-sm-12"}>
                        <h2 className="h3">Views</h2>


                    </div>
                </div>
            </Spinner>
        </>
    )
}

export default KeyspaceHome;
