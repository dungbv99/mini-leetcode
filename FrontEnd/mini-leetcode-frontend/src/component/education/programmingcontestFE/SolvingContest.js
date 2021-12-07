import {useEffect, useRef, useState} from "react";
import {request} from "./Request";
import {Link, useHistory, useParams} from "react-router-dom";
import Box from "@mui/material/Box";
import Typography from "@material-ui/core/Typography";
import PropTypes from "prop-types";
import {Button, Divider, Grid, MenuItem, Tab, TableHead, Tabs, TextField} from "@material-ui/core";
import * as React from 'react';
import Paper from "@material-ui/core/Paper";
import TableRow from "@material-ui/core/TableRow";
import {getColorLevel, getExtension, StyledTableCell, StyledTableRow} from "./lib";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
// import SplitterLayout from 'react-splitter-layout';
import {ScrollBox} from "react-scroll-box";
import {Markup} from "interweave";
import CodeMirror from "@uiw/react-codemirror";
import {API_URL} from "../../../config/config";
import {Console} from "./Console";
import SplitPane, { Pane } from 'react-split-pane';
import {TabPanelHorizontal, TabPanelVertical} from "./TabPanel";
import ContestRunTestCase from "./ContestRunTestCase";
import {useSelector} from "react-redux";
import {ConsoleContest} from "./ConsoleContest";
import {Test} from "./Test";
import {Timer} from "./Timer";



TabPanelHorizontal.propTypes = {
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
  const [source, setSource] = useState();
  const [screenHeight, setScreenHeight] = useState((window.innerHeight-180) + "px");
  const [computerLanguage, setComputerLanguage] = useState("CPP");
  const computerLanguageList = ["CPP", "GOLANG", "JAVA", "PYTHON3"];
  const [runCodeLoading, setRunCodeLoading] = useState(false);
  const [consoleTabIndex, setConsoleTabIndex] = useState(0);
  const [showConsole, setShowConsole] = useState(false);
  const [timeLimit, setTimeLimit] = useState(false);
  const [compileError, setCompileError] = useState(false);
  const [accept, setAccept] = useState(false);
  const [output, setOutput] = useState("");
  const [expected, setExpected] = useState();
  const [run, setRun] = useState(false);
  const [input, setInput] = useState();
  const [valueTab1, setValueTab1] =  useState(0);
  const [testCaseResult, setTestCaseResult] = useState();
  const [runTestCaseLoad, setRunTestCaseLoad] = useState(false);
  const [runTestCaseShow, setRunTestCaseShow] = useState(false);
  const [timer, setTimer] = useState('00:00:00');
  // const [test, setTest] = useState('00:00:00');
  const Ref = useRef(null);

  const handleValueTab1Change = (event, newValue) => {
    setValueTab1(newValue);
  };


  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const onInputChange = (input) =>{
    setInput(input);
  }
  const onChangeConsoleTabIndex = (value)=>{
    setConsoleTabIndex(value)
  }
  const handleScroll = () =>{
    if(showConsole){
      setScreenHeight((window.innerHeight-180) + "px");
      setShowConsole(false);
    }else{
      setScreenHeight((window.innerHeight-455) + "px");
      setShowConsole(true);
    }

  }

  const getTimeRemaining = (e) => {
    const total = Date.parse(e) - Date.parse(new Date());
    const seconds = Math.floor((total / 1000) % 60);
    const minutes = Math.floor((total / (1000 * 60)) % 60);
    const hours = Math.floor((total / (1000 * 60 * 60)) % 24);
    return {
      total, hours, minutes, seconds
    };
  }

  const clearTimer = (e) => {

    // If you adjust it you should also need to
    // adjust the Endtime formula we are about
    // to code next
    // If you try to remove this line the
    // updating of timer Variable will be
    // after 1000ms or 1sec
    if (Ref.current) clearInterval(Ref.current);
    const id = setInterval(() => {
      startTimer(e);
    }, 1000)
    console.log("done");

    Ref.current = id;

  }

  const startTimer = (e) => {
    // console.log("start timer ", e);
    let { total, hours, minutes, seconds } = getTimeRemaining(e);
    if (total >= 0) {

      // update the timer
      // check if less than 10 then we need to
      // add '0' at the begining of the variable
      let a = (hours > 9 ? hours : '0' + hours) + ':' +
        (minutes > 9 ? minutes : '0' + minutes) + ':' +
        (seconds > 9 ? seconds : '0' + seconds);
      // setTimer(a);


      // setTest(a);
      // setTimer(
      //   (hours > 9 ? hours : '0' + hours) + ':' +
      //   (minutes > 9 ? minutes : '0' + minutes) + ':'
      //   + (seconds > 9 ? seconds : '0' + seconds)
      // );
    }else{
    //  submit
    }
  }






  useEffect(() =>{

    request(
      "get",
      "/get-contest-detail/"+contestId,
      (res) =>{
        setContestTime(res.data.contestTime);
        setProblems(res.data.list);
        setContestName(res.data.contestName);
        console.log("res ", res.data);
        let arr = problems.map(()=>false);
        setSubmitted(arr);

        let a = "startTime-"+res.data.contestTime+"-"+contestId;

        if(localStorage.getItem(a) == null){
          console.log("set start time");
          let now = new Date();
          now.setMinutes(now.getMinutes()+res.data.contestTime%60);
          now.setHours(now.getHours()+res.data.contestTime/60);
          localStorage.setItem(a, now);
          clearTimer(now);
        }else{
          let now = new Date();
          if(localStorage.getItem(a) + res.data.contestTime < now.getHours()*60 + now.getMinutes()){
            // localStorage.removeItem(a);
          }else{
            clearTimer(new Date(localStorage.getItem(a)));
          }
        }
      }
    ).then();
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
              {/*<b><span style={{color:"#FFFFFF"}}>{`${timer}`}</span></b>*/}
              {contestTime !== undefined ? <Timer contestId={contestId} contestTime={contestTime}/> : <b><span style={{color:"#FFFFFF"}}>{`00:00:00`}</span></b>}
              {/*<Timer*/}
              {/*  contestId={contestId}*/}
              {/*  contestTime={contestTime}*/}
              {/*/>*/}

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

        <Box sx={{width:window.innerWidth, marginLeft:1, bgcolor: 'background.paper'}}>
          <TabPanelHorizontal value={value} index={0}>
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
          </TabPanelHorizontal>


          {
            problems.map((problem, index)=>{
            return(
              <TabPanelHorizontal value={value} index={index+1}>
                <SplitPane split="vertical"  primary={"second"} maxSize={"200px"}  >
                  <div >
                    <ScrollBox style={{width: '100%', overflow:"auto", height:(window.innerHeight-150) + "px"}}>
                      <Typography variant={"h5"}><b>{index+1}. {problem.problemName}</b></Typography>
                      <Divider />
                      <Markup content={problem.problemDescription} />
                    </ScrollBox>
                  </div>

                  <div>
                    <TextField
                      style={{width:0.075*window.innerWidth, marginLeft:20}}
                      variant={"outlined"}
                      size={"small"}
                      autoFocus
                      value={computerLanguage}
                      select
                      id="computerLanguage"
                      onChange={(event) => {
                        setComputerLanguage(event.target.value);
                      }}
                    >
                      {computerLanguageList.map((item) => (
                        <MenuItem key={item} value={item}>
                          {item}
                        </MenuItem>
                      ))}
                    </TextField>
                    <CodeMirror
                      height={screenHeight}
                      width="100%"
                      extensions={getExtension(computerLanguage)}
                      onChange={(value, viewUpdate) => {
                        setSource(value);
                      }}
                      autoFocus={false}
                    />

                    <ConsoleContest
                      showConsole={showConsole}
                      load={runCodeLoading}
                      output={output}
                      color={"light"}
                      extension={getExtension()}
                      input={input}
                      onInputChange={onInputChange}
                      consoleTabIndex={consoleTabIndex}
                      onChangeConsoleTabIndex={onChangeConsoleTabIndex}
                      accept={accept}
                      run={run}
                      timeLimit={timeLimit}
                      expected={expected}
                      compileError={compileError}
                      runTestCaseLoad={runTestCaseLoad}
                      runTestCaseShow={runTestCaseShow}
                      submitResult={testCaseResult}
                    />
                    <Button
                      variant="contained"
                      color="light"
                      // style={{marginLeft:"90px"}}
                      onClick={handleScroll}
                      // style={{position}}
                      // style={{left:"50%"}}
                      extension={getExtension()}
                    >
                      Console
                    </Button>

                    <Button
                      variant="contained"
                      color="light"
                      // style={{marginLeft:"90px"}}
                      // onClick={handleRunCode(problem.problemId)}
                      onClick={() =>{
                        console.log("problemId", problem.problemId);
                        setRunCodeLoading(true);
                        setConsoleTabIndex(1);
                        setShowConsole(true);
                        setScreenHeight((window.innerHeight-455) + "px");
                        let body =  {
                          sourceCode: source,
                          computerLanguage: computerLanguage,
                          input: input
                        }
                        request(
                          "post",
                          API_URL + "/problem-detail-run-code/" + problem.problemId,
                          (res) => {
                            setRun(true);
                            setRunCodeLoading(false);
                            if (res.data.status == "Time Limit Exceeded") {
                              setTimeLimit(true);
                              setCompileError(false);
                              setAccept(false);
                            } else if (res.data.status == "Compile Error") {
                              setTimeLimit(false);
                              setCompileError(true);
                              console.log("111");
                            } else if (res.data.status == "Accept") {
                              setAccept(true);
                              setTimeLimit(false);
                              setCompileError(false);
                            } else {
                              setAccept(false);
                              setTimeLimit(false);
                              setCompileError(false);
                            }
                            setOutput(res.data.output);
                            setExpected(res.data.expected);
                          },
                          {},
                          body
                        ).then();
                      }}
                      // style={{position}}
                      style={{marginLeft:"20px"}}
                    >
                      Run Code
                    </Button>

                    <Button
                      variant="contained"
                      color="light"
                      onClick={() =>{
                        setScreenHeight((window.innerHeight-455) + "px");
                        setRunTestCaseLoad(true);
                        setConsoleTabIndex(2);
                        setShowConsole(true);
                        setValueTab1(1);

                        let body ={
                          source: source,
                          language:computerLanguage,
                          contestId: contestId,
                          problemId: problem.problemId
                        };
                        request(
                          "post",
                          API_URL+"/contest-submit-problem",
                          (res) =>{
                            setTestCaseResult(res.data);
                            console.log("run all test case");
                            console.log("res ", res.data);

                          },
                          {},
                          body
                        ).then(
                          ()=>{
                            setRunTestCaseLoad(false);
                            setRunTestCaseShow(true);
                          }
                        );

                      }}
                      // style={{position}}
                      style={{marginLeft:"20px"}}
                    >
                      SUBMIT
                    </Button>

                  </div>
                </SplitPane>
                {/*<Test></Test>*/}
              </TabPanelHorizontal>
            );
          })
          }

        </Box>



      </Box>
    </div>



  );
}