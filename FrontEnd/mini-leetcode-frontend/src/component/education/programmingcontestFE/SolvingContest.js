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
import {ContestProblemComponent} from "./ContestProblemComponent";



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
  const [screenHeight, setScreenHeight] = useState((window.innerHeight-180) + "px");
  const [language, setLanguage] = useState("CPP");
  const [languageList, setLanguageList] = useState([]);
  const computerLanguageListDefault = ["CPP", "GOLANG", "JAVA", "PYTHON3"];
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
  const [listSource, setListSource] = useState([]);
  const [loadStupid, setLoadStupid] = useState(false);
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

  function submitContest(){

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
        for (let i = 0; i < res.data.list.length; i++){
          listSource.push("");
          languageList.push("CPP");
          let idSource = contestId+"-"+res.data.list[i].problemId+"-source";
          let source = localStorage.getItem(idSource);
          let idLanguage = contestId+"-"+res.data.list[i].problemId+"-language";
          let tmpLanguage = localStorage.getItem(idLanguage);
          if(source != null ){
            listSource[i] = source;
          }else{
            localStorage.setItem(idSource, "");
          }
          if(tmpLanguage != null){
            languageList[i] = tmpLanguage;
          }else{
            localStorage.setItem(idLanguage, "CPP");
          }

        }
        console.log("listSource ", listSource);
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
                {/*{contestId !== undefined ?*/}
                {/*  <ContestProblemComponent*/}
                {/*  contestId={contestId}*/}
                {/*  index={index}*/}
                {/*  problemDescription={problem.problemDescription}*/}
                {/*  problemId={problem.problemId}*/}
                {/*  problemName={problem.problemName}*/}
                {/*  submitted={submitted}*/}
                {/*/> : <div></div>}*/}

                <ContestProblemComponent
                  contestId={contestId}
                  index={index}
                  problemDescription={problem.problemDescription}
                  problemId={problem.problemId}
                  problemName={problem.problemName}
                  submitted={submitted}
                />



                {/*<SplitPane split="vertical"  primary={"second"} maxSize={"200px"}  >*/}
                {/*  <div >*/}
                {/*    <ScrollBox style={{width: '100%', overflow:"auto", height:(window.innerHeight-150) + "px"}}>*/}
                {/*      <Typography variant={"h5"}><b>{index+1}. {problem.problemName}</b></Typography>*/}
                {/*      <Divider />*/}
                {/*      <Markup content={problem.problemDescription} />*/}
                {/*    </ScrollBox>*/}
                {/*  </div>*/}

                {/*  <div>*/}
                {/*    <TextField*/}
                {/*      style={{width:0.075*window.innerWidth, marginLeft:20}}*/}
                {/*      variant={"outlined"}*/}
                {/*      size={"small"}*/}
                {/*      autoFocus*/}
                {/*      value={languageList[index]}*/}
                {/*      select*/}
                {/*      id="computerLanguage"*/}
                {/*      onChange={(event) => {*/}
                {/*        languageList[index] = event.target.value;*/}
                {/*        setLanguageList(languageList);*/}
                {/*        localStorage.setItem(contestId+"-"+problem.problemId+"-language", event.target.value);*/}
                {/*      }}*/}
                {/*    >*/}
                {/*      {computerLanguageListDefault.map((item) => (*/}
                {/*        <MenuItem key={item} value={item}>*/}
                {/*          {item}*/}
                {/*        </MenuItem>*/}
                {/*      ))}*/}
                {/*    </TextField>*/}
                {/*    <CodeMirror*/}
                {/*      value={listSource[index]}*/}
                {/*      height={screenHeight}*/}
                {/*      width="100%"*/}
                {/*      extensions={getExtension(language)}*/}
                {/*      onChange={(value, viewUpdate) => {*/}
                {/*        listSource[index] = value;*/}
                {/*        let a = contestId+"-"+problem.problemId+"-source";*/}
                {/*        localStorage.setItem(a, value);*/}
                {/*      }}*/}
                {/*      autoFocus={false}*/}
                {/*    />*/}

                {/*    <ConsoleContest*/}
                {/*      showConsole={showConsole}*/}
                {/*      load={runCodeLoading}*/}
                {/*      output={output}*/}
                {/*      color={"light"}*/}
                {/*      extension={getExtension()}*/}
                {/*      input={input}*/}
                {/*      onInputChange={onInputChange}*/}
                {/*      consoleTabIndex={consoleTabIndex}*/}
                {/*      onChangeConsoleTabIndex={onChangeConsoleTabIndex}*/}
                {/*      accept={accept}*/}
                {/*      run={run}*/}
                {/*      timeLimit={timeLimit}*/}
                {/*      expected={expected}*/}
                {/*      compileError={compileError}*/}
                {/*      runTestCaseLoad={runTestCaseLoad}*/}
                {/*      runTestCaseShow={runTestCaseShow}*/}
                {/*      submitResult={testCaseResult}*/}
                {/*    />*/}
                {/*    <Button*/}
                {/*      variant="contained"*/}
                {/*      color="light"*/}
                {/*      // style={{marginLeft:"90px"}}*/}
                {/*      onClick={handleScroll}*/}
                {/*      // style={{position}}*/}
                {/*      // style={{left:"50%"}}*/}
                {/*      extension={getExtension()}*/}
                {/*    >*/}
                {/*      Console*/}
                {/*    </Button>*/}

                {/*    <Button*/}
                {/*      variant="contained"*/}
                {/*      color="light"*/}
                {/*      // style={{marginLeft:"90px"}}*/}
                {/*      // onClick={handleRunCode(problem.problemId)}*/}
                {/*      onClick={() =>{*/}
                {/*        console.log("problemId", problem.problemId);*/}
                {/*        setRunCodeLoading(true);*/}
                {/*        setConsoleTabIndex(1);*/}
                {/*        setShowConsole(true);*/}
                {/*        setScreenHeight((window.innerHeight-455) + "px");*/}
                {/*        console.log(listSource[index]);*/}
                {/*        let body =  {*/}
                {/*          sourceCode: listSource[index],*/}
                {/*          computerLanguage: language,*/}
                {/*          input: input*/}
                {/*        }*/}
                {/*        request(*/}
                {/*          "post",*/}
                {/*          API_URL + "/problem-detail-run-code/" + problem.problemId,*/}
                {/*          (res) => {*/}
                {/*            setRun(true);*/}
                {/*            setRunCodeLoading(false);*/}
                {/*            if (res.data.status == "Time Limit Exceeded") {*/}
                {/*              setTimeLimit(true);*/}
                {/*              setCompileError(false);*/}
                {/*              setAccept(false);*/}
                {/*            } else if (res.data.status == "Compile Error") {*/}
                {/*              setTimeLimit(false);*/}
                {/*              setCompileError(true);*/}
                {/*              console.log("111");*/}
                {/*            } else if (res.data.status == "Accept") {*/}
                {/*              setAccept(true);*/}
                {/*              setTimeLimit(false);*/}
                {/*              setCompileError(false);*/}
                {/*            } else {*/}
                {/*              setAccept(false);*/}
                {/*              setTimeLimit(false);*/}
                {/*              setCompileError(false);*/}
                {/*            }*/}
                {/*            setOutput(res.data.output);*/}
                {/*            setExpected(res.data.expected);*/}
                {/*          },*/}
                {/*          {},*/}
                {/*          body*/}
                {/*        ).then();*/}
                {/*      }}*/}
                {/*      // style={{position}}*/}
                {/*      style={{marginLeft:"20px"}}*/}
                {/*    >*/}
                {/*      Run Code*/}
                {/*    </Button>*/}

                {/*    <Button*/}
                {/*      variant="contained"*/}
                {/*      color="light"*/}
                {/*      onClick={() =>{*/}
                {/*        setScreenHeight((window.innerHeight-455) + "px");*/}
                {/*        setRunTestCaseLoad(true);*/}
                {/*        setConsoleTabIndex(2);*/}
                {/*        setShowConsole(true);*/}
                {/*        setValueTab1(1);*/}

                {/*        let body ={*/}
                {/*          source: listSource[index],*/}
                {/*          language:language,*/}
                {/*          contestId: contestId,*/}
                {/*          problemId: problem.problemId*/}
                {/*        };*/}
                {/*        request(*/}
                {/*          "post",*/}
                {/*          API_URL+"/contest-submit-problem",*/}
                {/*          (res) =>{*/}
                {/*            setTestCaseResult(res.data);*/}
                {/*            console.log("run all test case");*/}
                {/*            console.log("res ", res.data);*/}

                {/*          },*/}
                {/*          {},*/}
                {/*          body*/}
                {/*        ).then(*/}
                {/*          ()=>{*/}
                {/*            setRunTestCaseLoad(false);*/}
                {/*            setRunTestCaseShow(true);*/}
                {/*            submitted[index] = true;*/}
                {/*          }*/}
                {/*        );*/}

                {/*      }}*/}
                {/*      // style={{position}}*/}
                {/*      style={{marginLeft:"20px"}}*/}
                {/*    >*/}
                {/*      SUBMIT*/}
                {/*    </Button>*/}

                {/*  </div>*/}
                {/*</SplitPane>*/}
              </TabPanelHorizontal>
            );
          })
          }

        </Box>



      </Box>
    </div>



  );
}




