import * as React from "react";
import {Link, useParams, NavLink} from "react-router-dom";
import {Fragment, useEffect, useState} from "react";
import {request} from "./Request";
import Typography from "@mui/material/Typography";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@mui/material/Table";
import {Button, Grid, MenuItem, TableHead, TextField} from "@material-ui/core";
import TableRow from "@material-ui/core/TableRow";
import {getColorLevel, getColorRegisterStatus, Search, SearchIconWrapper, StyledTableCell, StyledTableRow} from "./lib";
import TableBody from "@mui/material/TableBody";
import Pagination from "@material-ui/lab/Pagination";
import {API_URL} from "../../../config/config";
import {successNoti} from "../../../utils/notification";
import Box from "@mui/material/Box";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import SearchIcon from "@mui/icons-material/Search";
import {InputBase} from "@mui/material";
// import { HashLink } from 'react-router-hash-link';


export function ContestManager(){
  const {contestId} = useParams();
  const [contestName, setContestName] = useState();
  const [contestTime, setContestTime] = useState();
  const [problems, setProblems] = useState([]);
  const [timeLimit, setTimeLimit] = useState();
  const [pendings, setPendings] = useState([]);
  const [pagePendingSize, setPagePendingSize] = useState(50);
  const [pageSuccessfulSize, setPageSuccessfulSize] = useState(50);
  const pageSizes = [50,100, 150];
  const [totalPagePending, setTotalPagePending] = useState(0);
  const [totalPageSuccessful, setTotalPageSuccessful] = useState(0);
  const [pagePending, setPagePending] = useState(1);
  const [pageSuccessful, setPageSuccessful] = useState(1);
  const [successful, setSuccessful] = useState([]);
  const [load, setLoad] = useState(true);
  const [pageRanking, setPageRanking] = useState(1);
  const [ranking, setRanking] = useState([]);
  const [totalPageRanking, setTotalPageRanking] = useState(0);
  const [pageRankingSize, setPageRankingSize] = useState(50);

  const [searchUsers, setSearchUsers] = useState([]);
  const [pageSearchSize, setPageSearchSize] = useState(50);
  const [totalPageSearch, setTotalPageSearch] = useState(0);
  const [pageSearch, setPageSearch] = useState(1);
  const [keyword, setKeyword]= useState("");

  const handlePageSearchSizeChange = (event) => {
    setPageSearchSize(event.target.value);
    setPageSearch(1);
  }

  const handlePagePendingSizeChange = (event) => {
    setPagePendingSize(event.target.value);
    setPagePending(1);
    // getProblemContestList();
  };

  const handlePageRankingSizeChange = (event) =>{
    setPageRankingSize(event.target.value);
    setPageRanking(1);
  }

  const handlePageSuccessfulSizeChange = (event) => {
    setPageSuccessfulSize(event.target.value);
    setPageSuccessful(1);
  }

  async function searchUser(){

  }


  function getUserPending(){
    request(
      "get",
      API_URL+"/get-user-register-pending-contest/"+contestId+"?size="+pagePendingSize+"&page="+(pagePending-1),
      (res) => {
        console.log("res pending", res.data);
        setPendings(res.data.contents.content);
        setTotalPagePending(res.data.contents.totalPages);
      }
    ).then();
  }

  function getUserSuccessful(){
    request(
      "get",
      API_URL+"/get-user-register-successful-contest/"+contestId+"?size="+pageSuccessfulSize+"&page="+(pageSuccessful-1),
      (res) => {
        console.log("res pending", res.data);
        setSuccessful(res.data.contents.content);
        setTotalPagePending(res.data.contents.totalPages);
      }
    ).then();
  }

  function getRanking(){
    request(
      "get",
      API_URL+"/get-ranking-contest/"+contestId+"?size="+pageRankingSize+"&page="+(pageRanking-1),
      (res) =>{
        console.log("ranking ", res.data);
        setTotalPageRanking(res.data.totalPages);
        setRanking(res.data.content);
      }
    ).then();
  }

  function recalculatedRanking(){
    request(
      "post",
        API_URL+"/recalculate-ranking/"+contestId
    ).then(() =>{
      getRanking();
    })
  }

  function searchUser(keyword){
    request(
      "get",
      API_URL+"/search-user/"+contestId+"?size="+pageSearchSize+"&page="+(pageSearch-1)+"&keyword="+keyword,
      (res) => {
        console.log("res search", res);
        setSearchUsers(res.data.contents.content);
        setTotalPageSearch(res.data.contents.totalPages);
      }
    ).then();
  }

  useEffect(() =>{
    request(
      "get",
      "/get-contest-detail/"+contestId,
      (res)=>{
        setContestTime(res.data.contestTime);
        setProblems(res.data.list);
        setContestName(res.data.contestName);
        setTimeLimit(res.data.contestTime);
      }
    ).then();

    getUserPending();
    getUserSuccessful()
    getRanking();
    searchUser(keyword);
  },[])



  return(
    <div>
      <Typography variant="h4" component="h2">
        Contest: {contestName}
      </Typography>
      <Typography variant="h5" component="h2">
        Time Limit: {timeLimit} minutes
      </Typography>
      <Typography variant="h5" component="h2" style={{marginTop:10, marginBottom:10}}>
        List Problem
      </Typography>

      <TableContainer component={Paper}>
        <Table sx={{minWidth:window.innerWidth-500}}  aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell></StyledTableCell>
              <StyledTableCell >Question</StyledTableCell>
              <StyledTableCell align="center">Level</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {problems.map((problem, index) =>(
              <StyledTableRow>
                <StyledTableCell>
                  <b>{index+1}</b>
                </StyledTableCell>
                <StyledTableCell component="th" scope="row">
                  <b>{problem.problemName}</b>
                </StyledTableCell>
                <StyledTableCell component="th" scope="row" align="center">
                  <span style={{color:getColorLevel(`${problem.levelId}`)}}> <b>{`${problem.levelId}`} </b> </span>
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>






        </Table>
      </TableContainer>


      <section id={"#registered"}>
        <Typography variant="h5" component="h2" style={{marginTop:10, marginBottom:10}}>
          List Student Registered Contest
        </Typography>
      </section>
      <RegisteredTable
        successful={successful}
        pageSuccessful={pageSuccessful}
        pageSuccessfulSize={pageSuccessfulSize}
        load={load}
      />


      <br></br>
      <Grid container spacing={12}>
        <Grid item xs={6}>

          <TextField
            variant={"outlined"}
            autoFocus
            size={"small"}
            required
            select
            id="pageSize"
            value={pageSuccessfulSize}
            onChange={handlePageSuccessfulSizeChange}
          >
            {pageSizes.map((item) => (
              <MenuItem key={item} value={item}>
                {item}
              </MenuItem>
            ))}
          </TextField>
        </Grid>

        <Grid item >
          <Pagination
            className="my-3"
            count={totalPageSuccessful}
            page={pageSuccessful}
            siblingCount={1}
            boundaryCount={1}
            variant="outlined"
            shape="rounded"
            onChange={(event, value) =>{
              setPageSuccessful(value);

            }}
          />
        </Grid>
      </Grid>


      {
        pendings.length > 0 ?
          <div>
            <section id={"#pending"}>
              <Typography variant="h5" component="h2" style={{marginTop:10, marginBottom:10}}>
                List Student Request
              </Typography>
            </section>
            <TableContainer component={Paper}>
              <Table sx={{minWidth:window.innerWidth-500}}  aria-label="customized table">
                <TableHead>
                  <TableRow>
                    <StyledTableCell></StyledTableCell>
                    <StyledTableCell align="center">User Name</StyledTableCell>
                    <StyledTableCell align="center">Full Name</StyledTableCell>
                    <StyledTableCell align="center">Email</StyledTableCell>
                    <StyledTableCell align="center">Approve</StyledTableCell>
                    <StyledTableCell align="center">Reject</StyledTableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {
                    pendings.map((s, index) =>(
                      <StyledTableRow>
                        <StyledTableCell>
                          <b>{index+1+(pagePending-1)*pageSuccessfulSize}</b>
                        </StyledTableCell>

                        <StyledTableCell align="center">
                          <b>{s.userName}</b>
                        </StyledTableCell>

                        <StyledTableCell align="center">
                          <b>{s.firstName}{" "}{s.middleName}{" "}{s.lastName}</b>
                        </StyledTableCell>

                        <StyledTableCell align="center">
                          <b>{s.email}</b>
                        </StyledTableCell>

                        <StyledTableCell align="center">
                          <Button
                            variant="contained"
                            color="light"
                            onClick={() => {
                              let body = {
                                contestId: contestId,
                                userId: s.userName,
                                status: "SUCCESSES"
                              }
                              request(
                                "post",
                                "/techer-manager-student-register-contest",
                                ()=>{
                                  successful.push(s);
                                  // setSuccessful(successful)
                                  // setSuccessful(successful)
                                  pendings.splice(index,1);
                                  // setPendings(pendings);
                                  console.log("successful ", successful);
                                  console.log("pendings ", pendings);
                                  setLoad(false);
                                  setLoad(true);
                                },
                                {}
                                ,
                                body

                              ).then()
                            }}
                          >
                            Approve
                          </Button>
                        </StyledTableCell>

                        <StyledTableCell align="center">
                          <Button
                            variant="contained"
                            color="light"
                            onClick={() => {
                              let body = {
                                contestId: contestId,
                                userId: s.userName,
                                status: "FAILED"
                              }
                              request(
                                "post",
                                "/techer-manager-student-register-contest",
                                ()=>{
                                  pendings.splice(index,1);
                                  setLoad(false);
                                  setLoad(true)
                                },
                                {}
                                ,
                                body

                              ).then()
                            }}
                          >
                            Reject
                          </Button>
                        </StyledTableCell>

                      </StyledTableRow>
                    ))
                  }
                </TableBody>
              </Table>
            </TableContainer>

            <br></br>
            <Grid container spacing={12}>
              <Grid item xs={6}>

                <TextField
                  variant={"outlined"}
                  autoFocus
                  size={"small"}
                  required
                  select
                  id="pageSize"
                  value={pagePendingSize}
                  onChange={handlePagePendingSizeChange}
                >
                  {pageSizes.map((item) => (
                    <MenuItem key={item} value={item}>
                      {item}
                    </MenuItem>
                  ))}
                </TextField>
              </Grid>

              <Grid item >
                <Pagination
                  className="my-3"
                  count={totalPagePending}
                  page={pagePending}
                  siblingCount={1}
                  boundaryCount={1}
                  variant="outlined"
                  shape="rounded"
                  onChange={(event, value) =>{
                    setPagePending(value);
                    getUserPending();
                  }}
                />
              </Grid>
            </Grid>
          </div>

          :
          <div></div>
      }


      <br/><br/><br/>




      <div>
        <section id={"#ranking"}>
          <Typography variant="h5" component="h2" style={{marginTop:10, marginBottom:10}}>
            Add Member
          </Typography>
        </section>
      
        <Box sx={{ flexGrow: 1, marginBottom: 2 }}>
          <AppBar position="static" color={"transparent"}>
            <Toolbar>
              <Search>
                <SearchIconWrapper>
                  <SearchIcon />
                </SearchIconWrapper>
                <InputBase
                  style={{paddingLeft:50}}
                  placeholder={"search..."}
                  onChange={(event) =>{
                    searchUser(event.target.value);
                  }}
                />
              </Search>
            </Toolbar>
          </AppBar>
        </Box>
      
      
        <TableContainer component={Paper}>
          <Table sx={{minWidth:window.innerWidth-500}}  aria-label="customized table">
            <TableHead>
              <TableRow>
                <StyledTableCell align="center"></StyledTableCell>
                <StyledTableCell align="center">User Name</StyledTableCell>
                <StyledTableCell align="center">Full Name</StyledTableCell>
                <StyledTableCell align="center">Email</StyledTableCell>
                <StyledTableCell align="center">Status</StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {
                searchUsers.map((s, index) =>(
                  <StyledTableRow>
                    <StyledTableCell>
                      <b>{index+1+(pageSuccessful-1)*pageSuccessfulSize}</b>
                    </StyledTableCell>
      
                    <StyledTableCell align="center">
                      <b>{s.userName}</b>
      
                    </StyledTableCell>
      
                    <StyledTableCell align="center">
                      <b>{s.firstName}{" "}{s.middleName}{" "}{s.lastName}</b>
      
                    </StyledTableCell>
      
                    <StyledTableCell align="center">
                      <b>{s.email}</b>
                    </StyledTableCell>
      
                    <StyledTableCell align="center">
                      {
                        s.status != null ?
                          <b><span style={{color:getColorRegisterStatus(`${s.status}`)}}>{`${s.status}`}</span></b>:
                          <b><span style={{color:getColorRegisterStatus('NOT REGISTER')}}>NOT REGISTER</span></b>
                      }

                    </StyledTableCell>
                  </StyledTableRow>
                ))
              }
            </TableBody>
          </Table>
        </TableContainer>
      
      
      
      
        <br/><br/>
      </div>




      {
        ranking.length > 0 ?
          <div>
            <section id={"#ranking"}>
              <Typography variant="h5" component="h2" style={{marginTop:10, marginBottom:10}}>
                Contest Ranking
              </Typography>
            </section>


            <TableContainer component={Paper}>
              <Table sx={{minWidth:window.innerWidth-500}}  aria-label="customized table">
                <TableHead>
                  <TableRow>
                    <StyledTableCell align="center"></StyledTableCell>
                    <StyledTableCell align="center">User Name</StyledTableCell>
                    <StyledTableCell align="center">Full Name</StyledTableCell>
                    <StyledTableCell align="center">Email</StyledTableCell>
                    <StyledTableCell align="center">Point</StyledTableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {
                    ranking.map((s, index) =>(
                      <StyledTableRow>
                        <StyledTableCell>
                          <b>{index+1+(pageSuccessful-1)*pageSuccessfulSize}</b>
                        </StyledTableCell>

                        <StyledTableCell align="center">
                          <b>{s.userId}</b>

                        </StyledTableCell>

                        <StyledTableCell align="center">
                          <b>{s.fullName}</b>

                        </StyledTableCell>

                        <StyledTableCell align="center">
                          <b>{s.email}</b>
                        </StyledTableCell>

                        <StyledTableCell align="center">
                          <b>{s.point}</b>
                        </StyledTableCell>

                      </StyledTableRow>
                    ))
                  }
                </TableBody>
              </Table>
            </TableContainer>


            <br></br>
            <Grid container spacing={12}>
              <Grid item xs={6}>

                <TextField
                  variant={"outlined"}
                  autoFocus
                  size={"small"}
                  required
                  select
                  id="pageSize"
                  value={pageRankingSize}
                  onChange={handlePageRankingSizeChange}
                >
                  {pageSizes.map((item) => (
                    <MenuItem key={item} value={item}>
                      {item}
                    </MenuItem>
                  ))}
                </TextField>
              </Grid>

              <Grid item >
                <Pagination
                  className="my-3"
                  count={totalPageRanking}
                  page={pageRanking}
                  siblingCount={1}
                  boundaryCount={1}
                  variant="outlined"
                  shape="rounded"
                  onChange={(event, value) =>{
                    setPageRanking(value);
                    getRanking();
                  }}
                />
              </Grid>
            </Grid>


            <Button
              variant="contained"
              color="light"
              style={{marginLeft:"45px"}}
              onClick={recalculatedRanking}
            >
              Recalculate Ranking
            </Button>
          </div>
          :
          <div></div>

      }

    </div>
  );
}

function RegisteredTable(props){
  const {successful, pageSuccessful, pageSuccessfulSize, load} = props;
  if(load){
    return(
      <div>
        <TableContainer component={Paper}>
          <Table sx={{minWidth:window.innerWidth-500}}  aria-label="customized table">
            <TableHead>
              <TableRow>
                <StyledTableCell align="center"></StyledTableCell>
                <StyledTableCell align="center">User Name</StyledTableCell>
                <StyledTableCell align="center">Full Name</StyledTableCell>
                <StyledTableCell align="center">Email</StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {
                successful.map((s, index) =>(
                  <StyledTableRow>
                    <StyledTableCell>
                      <b>{index+1+(pageSuccessful-1)*pageSuccessfulSize}</b>
                    </StyledTableCell>

                    <StyledTableCell align="center">
                      <b>{s.userName}</b>

                    </StyledTableCell>

                    <StyledTableCell align="center">
                      <b>{s.firstName}{" "}{s.middleName}{" "}{s.lastName}</b>

                    </StyledTableCell>

                    <StyledTableCell align="center">
                      <b>{s.email}</b>
                    </StyledTableCell>

                  </StyledTableRow>
                ))
              }
            </TableBody>
          </Table>
        </TableContainer>
      </div>
    );
  }else{
    return (
      <div></div>
    );
  }

}