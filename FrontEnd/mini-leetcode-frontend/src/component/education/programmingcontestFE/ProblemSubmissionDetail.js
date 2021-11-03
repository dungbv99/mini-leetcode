import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {request} from "./Request";
import {API_URL} from "../../../config/config";
import * as React from "react";
import {Typography} from "@mui/material";
import Box from "@mui/material/Box";
import {Grid} from "@material-ui/core";

function ProblemSubmissionDetail(){
  const {problemSubmissionId} = useParams();
  const [memoryUsage, setMemoryUsage] = useState();
  const [problemId, setProblemId] = useState();
  const [problemName, setProblemName] = useState();
  const [runTime, setRunTime] = useState();
  const [score, setScore] = useState();
  const [submissionLanguage, setSubmissionLanguage] = useState();
  const [submissionSource, setSubmissionSource] = useState();
  const [submittedAt, setSubmittedAt] = useState();
  const [testCasePass, setTestCasePass] = useState();
  const [status, setStatus] = useState();
  const getStatusColor = (status) => {
    switch (status){
      case 'Accept':
        return 'green';
      default:
        return 'red';
    }
  }

  useEffect(() => {
    console.log("problemSubmissionId ", problemSubmissionId);
    request(
      "get",
      API_URL+"/get-problem-submission/"+problemSubmissionId,
      (res)=>{
        setMemoryUsage(res.data.memoryUsage);
        setProblemId(res.data.problemId);
        setProblemName(res.data.problemName);
        setRunTime(res.data.runTime);
        setScore(res.data.score);
        setSubmissionLanguage(res.data.submissionLanguage);
        setSubmissionSource(res.data.submissionSource);
        setSubmittedAt(res.data.submittedAt);
        setTestCasePass(res.data.testCasePass);
        setStatus(res.data.status);
      },
      {}
    ).then()
  })
  return (
    <div>
      <Typography variant={"h5"}>
        <Link to={"/programming-contest/problem-detail/"+problemId}  style={{ textDecoration: 'none', color:"black", cursor:""}} >
          <span style={{color:"#00D8FF"}}>{problemName}</span>
        </Link>
      </Typography>
      <Typography variant={"h4"}>
        Submission Detail
      </Typography>

      <Box sx={{ width: '100%', bgcolor: 'background.paper', height:"120px" , border: "1px solid black", padding: "10px", justifyItems:"center", justifySelf:"center"}}>
        <Grid container alignItems="center">
          <Grid item xs>
            <Typography variant="h6" >
              <b>{testCasePass}</b> test cases passed.
            </Typography>
          </Grid>
          <Grid item xs>
            <Typography variant="h5" align="right">
              status: <span  style={{color:getStatusColor(`${status}`)}}>{`${status}`}</span>
            </Typography>
          </Grid>

        </Grid>
        <Grid container alignItems="center">
          <Grid item xs>
            <Typography variant="h6" >
              Run Time: <i>{runTime}</i><br/>
              Memory Usage: <i>{memoryUsage} kb</i>
            </Typography>
          </Grid>
          <Grid item xs>
            <Typography variant="h6" align="right">
              Submitted: {submittedAt}
            </Typography>
          </Grid>

        </Grid>
      </Box>

    </div>
  )
}
export default ProblemSubmissionDetail;
