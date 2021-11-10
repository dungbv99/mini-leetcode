import {useEffect, useState} from "react";
import {request} from "./Request";
import {Link, useHistory, useParams} from "react-router-dom";
import Box from "@mui/material/Box";
import Typography from "@material-ui/core/Typography";
import PropTypes from "prop-types";
import {Button, Grid, Tab, TableHead, Tabs} from "@material-ui/core";
import * as React from 'react';
import Paper from "@material-ui/core/Paper";
import TableRow from "@material-ui/core/TableRow";
import {getColorLevel, StyledTableCell, StyledTableRow} from "./lib";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`vertical-tabpanel-${index}`}
      aria-labelledby={`vertical-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) {
  return {
    id: `vertical-tab-${index}`,
    'aria-controls': `vertical-tabpanel-${index}`,
  };
}


export default function SolvingContest(){
  const {contestId} = useParams();
  const [value, setValue] = React.useState(0);
  const [contestName, setContestName] = useState();
  const [contestTime, setContestTime] = useState();
  const [problems, setProblems] = useState([]);
  const [submitted, setSubmitted] = useState([]);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };


  useEffect(() =>{
    request(
      "get",
      "/get-contest-detail/"+contestId,
      (res) =>{
        console.log("res ", res.data);
        setProblems(res.data.list);
        setContestTime(res.data.contestTime);
        setContestName(res.data.contestName);

        let arr = problems.map(()=>false);
        setSubmitted(arr);
      }
    ).then(
    );
  }, [])

  return (
    <div>
      <Box
        sx={{ flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height:window.innerHeight - 100,  }}
      >
        <Box>
          <Box
            sx={{flexGrow: 1, bgcolor: '#000000', display: 'flex', height:80}}
          >
            <Grid container alignItems="center" style={{padding:"10px"}}>
              <span style={{color:"#FFFFFF"}}>{`${contestTime}m left`}</span>
            </Grid>

          </Box>
          <Tabs
            orientation="vertical"
            variant="scrollable"
            value={value}
            onChange={handleChange}
            indicatorColor={"primary"}
            aria-label="Vertical tabs example"
            sx={{ borderRight: 1, borderColor: 'divider', width: 80 }}
          >
            <Tab label="ALL" {...a11yProps(0)} style={{minWidth: 80}}/>
            {
              problems.map((problem, index) =>{
                return(
                  <Tab label={index+1} {...a11yProps(problem.problemId)} style={{minWidth: 80}}/>
                );

              })
            }
          </Tabs>
        </Box>

        <TabPanel value={value} index={0}>
          <TableContainer component={Paper}>
            <Table sx={{minWidth:window.innerWidth-500}}  aria-label="customized table">
              <TableHead>
                <TableRow>
                  <StyledTableCell>Question</StyledTableCell>
                  <StyledTableCell align="center">Level</StyledTableCell>
                  <StyledTableCell align="right">Action</StyledTableCell>

                </TableRow>
              </TableHead>
              <TableBody>
                {problems.map((problem, index) =>(
                  <StyledTableRow>
                    <StyledTableCell component="th" scope="row">
                      {problem.problemName}
                    </StyledTableCell>
                    <StyledTableCell component="th" scope="row" align="center">
                      <span style={{color:getColorLevel(`${problem.levelId}`)}}>{`${problem.levelId}`}</span>
                    </StyledTableCell>
                    <StyledTableCell align="right">
                      <Button
                        variant="contained"
                        color="light"
                        // style={{marginLeft:"90px"}}
                        onClick={() =>{
                          setValue(index+1)
                        }}
                        // style={{position}}
                        style={{marginLeft:"20px"}}
                      >
                        {submitted[index] ? "Modify" : "Solve"}
                      </Button>

                    </StyledTableCell>
                  </StyledTableRow>
                ))}
              </TableBody>

            </Table>
          </TableContainer>
        </TabPanel>

        {problems.map((problem, index)=>{
          return(
            <TabPanel value={value} index={index+1}>
              <Button
                variant="contained"
                color="light"
                // style={{marginLeft:"90px"}}
                onClick={() =>{
                  submitted[index] = true;
                }}
                // style={{position}}
                style={{marginLeft:"20px"}}
              >
                Submit
              </Button>
            </TabPanel>
          )
        })}



      </Box>
    </div>



  );
}