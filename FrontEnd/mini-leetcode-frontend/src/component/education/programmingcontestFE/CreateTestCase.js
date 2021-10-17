import {Box, Button, Grid, MenuItem, Tab, Tabs, TextField, Toolbar} from "@material-ui/core";
import * as React from "react";
import {useEffect, useState} from "react";
import Typography from "@mui/material/Typography";
import {ScrollBox} from "react-scroll-box";
import {a11yProps, TabPanel} from "./TabPanel";
import CodeMirror from "@uiw/react-codemirror";
import {useParams} from "react-router-dom";
import {authGet} from "../../../api";
import {useDispatch, useSelector} from "react-redux";


export default function CreateTestCase(props){
  const [value, setValue] = useState(0);
  const [input, setInput] = useState();
  const [result, setResult] = useState();
  const [screenHeight, setScreenHeight] = useState((window.innerHeight-300)/2 + "px");
  const {problemId} = useParams();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.auth.token);
  const [problem, setProblem] = useState();
  const [description, setDescription] = useState();
  const [solution, setSolution] = useState();

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  useEffect(() =>{
    console.log("problemId ", problemId);
    console.log("token ", token);
    authGet(dispatch,token, "/problem-details/"+problemId).then(
      (res) =>{
        console.log("res ", res);
        setProblem(res);
        setDescription(res.problemDescription);
        setSolution(res.solution);
      }
    );
  },[])

  return(
    <div>

      <Grid container spacing={12}>
        <Grid item xs={6}>
          <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
            <Toolbar style={{height:"0px", marginTop:"-12px", marginBottom:"-8px", border:"1px solid transparent", position: "relative", width:"100%"}} color={"default"} >
              <Tabs
                value={value}
                onChange={handleChange}
                indicatorColor={"primary"}
                autoFocus
                style={{
                  width:"100%",
                  display:"inline-table",
                  border: "1px solid transparent ",
                  position: "relative",
                  borderBottom:"none",

                }}
                variant={"fullWidth"}
                aria-label="basic tabs example"
              >
                <Tab label="Description" {...a11yProps(0)} style={{width:"50%"}}/>
                <Tab label="Solution" {...a11yProps(1)} style={{width:"50%"}}/>

              </Tabs>
            </Toolbar>
          </Box>
          <TabPanel value={value} index={0}>
            <ScrollBox style={{width: '100%', overflow:"auto", height:(window.innerHeight-180) + "px"}}>
              {/*<Typography variant={"h4"} color={"#d6d6d6"}  >*/}
              {/*  /!*{problem.problemDescription}*!/*/}
              {/*  description*/}
              {/*</Typography>*/}
              {description}
            </ScrollBox>

          </TabPanel>

          <TabPanel value={value} index={1}>
            <ScrollBox style={{width: '100%', overflow:"auto", height:(window.innerHeight-180) + "px"}}>
              <Typography variant={"h4"} color={"#d6d6d6"}  >
                {solution}
              </Typography>
            </ScrollBox>
          </TabPanel>

        </Grid>
        <Grid item xs={6}>
          <Typography variant={"h5"}>
            Testcase
          </Typography>
          <CodeMirror
            height={"200px"}
            width="100%"
            onChange={(value, viewUpdate) => {
              setInput(value);
            }}
            autoFocus={false}
          />
          <br/><br/>
          <Typography variant={"h5"}>
            Result
          </Typography>
          <CodeMirror
            height={"200px"}
            width="100%"
            value={result}
            autoFocus={false}
            editable={false}
          />
          <Button
            variant="contained"
            color="light"

            style={{marginTop:"10px", marginLeft:"50px"}}
          >
            get testcase result
          </Button>
          <Button
            variant="contained"
            color="light"

            style={{ marginLeft:"50px", marginTop:"10px"}}
          >
            save test case
          </Button>
        </Grid>
      </Grid>


    </div>





  );
}