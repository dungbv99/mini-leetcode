import * as React from 'react';
import Typography from '@mui/material/Typography';
import {useState} from "react";
import {cpp, cppLanguage} from '@codemirror/lang-cpp';
import {java} from '@codemirror/lang-java';
import {pythonLanguage} from '@codemirror/lang-python';
import { go } from '@codemirror/legacy-modes/mode/go';
import { javascript } from '@codemirror/lang-javascript';
import { StreamLanguage } from '@codemirror/stream-parser';
import CodeMirror from "@uiw/react-codemirror";
import {
  AppBar,
  Tabs,
  Tab,
  Toolbar,
  Box,
  TextField,
  Grid, MenuItem, Button,
} from "@material-ui/core";
import { MuiThemeProvider, createTheme, makeStyles } from "@material-ui/core/styles";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
          <Typography>{children}</Typography>

      )}
    </div>
  );
}
// TabPanel.propTypes = {
//   children: PropTypes.node,
//   index: PropTypes.any.isRequired,
//   value: PropTypes.any.isRequired,
// };

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,

  };
}

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    height: theme.navBarHeight
  },
  // tabIndicator: {
  //   backgroundColor: PRIMARY_RED.default
  // },
  tabBar: {
    top: '80px'
  },
  rightAlign: {
    marginLeft: 'auto',
  },
  appBarContainer : {
    padding : '10px 20px'
    , display : 'flex'
    , flexFlow : 'row nowrap'
    , justifyContent : 'space-between'
    , marginBottom : '0px ' // removing !important doesn't help
    , '& > *' : {
      border : "2px dashed back"
    }
  }
}));




export default function ProblemDetail(){
  const [value, setValue] = useState(0);
  const [color, setColor] = useState("light");
  const colorList = ["light", "dark"];
  const [computerLanguage, setComputerLanguage] = useState("CPP");
  const computerLanguageList = ["CPP", "GOLANG", "JAVA", "PYTHON3"];
  const classes = useStyles();
  const [source, setSource] = useState();
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const [screenHeight, setScreenHeight] = useState((window.innerHeight-190) + "px");
  const getExtension = () =>{
    switch (computerLanguage){
      case "CPP":
        return [cppLanguage];
      case "GoLang":
        return StreamLanguage.define(go);
      case "Java":
        return java();
      case "Python3":
        return StreamLanguage.define(pythonLanguage);
      default:
        return javascript();
    }
  }

  return (
    <div onScroll={false}>

      <AppBar position="static" color={"default"} variant={"outlined"} style={{width: "100%", height: "65px", marginTop:"0px"}} >

        <Box >
          <Toolbar>
            <Tabs
              value={value}
              onChange={handleChange}
              indicatorColor={"primary"}
              autoFocus
              style={{width:"41.667%"}}
              variant={"fullWidth"}

            >
              <Tab label="Description" {...a11yProps(0)} />
              <Tab label="Solution" {...a11yProps(1)}/>
              <Tab label="Discuss" {...a11yProps(2)}/>
              <Tab label="Submissions" {...a11yProps(3)}/>
            </Tabs>
            <div>

              <TextField
                style={{width:"125px", margin:20}}
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
              <TextField
                style={{width:"100px", margin:20}}
                variant="outlined"
                size="small"
                autoFocus
                // required
                value={color}
                select
                id="color"
                onChange={(event) => {
                  setColor(event.target.value);
                }}
              >
                {colorList.map((item) => (
                  <MenuItem key={item} value={item}>
                    {item}
                  </MenuItem>
                ))}
              </TextField>
            </div>
          </Toolbar>


        </Box>
      </AppBar>
      {/*</MuiThemeProvider>*/}




      <Grid container spacing={12}>
        <Grid item xs={5}>
          <TabPanel value={value} index={0}>
          </TabPanel>
          <TabPanel value={value} index={1}>
          </TabPanel>
          <TabPanel value={value} index={2}>
          </TabPanel>
          <TabPanel value={value} index={3}>
          </TabPanel>
        </Grid>
        <Grid item xs={7}>
          <CodeMirror
            height={screenHeight}
            width="100%"
            extensions={getExtension()}
            onChange={(value, viewUpdate) => {
              setSource(value);
            }}
            autoFocus={false}
            theme={color}
          />

        </Grid>
      </Grid>



      {/*<Button*/}
      {/*  variant="contained"*/}
      {/*  color="light"*/}
      {/*  // style={{marginLeft:"90px"}}*/}
      {/*  // onClick={handleRun}*/}
      {/*  // style={{position}}*/}
      {/*  style={{left:"41.6%"}}*/}
      {/*>*/}
      {/*  Run Code*/}
      {/*</Button>*/}

      <Button
        variant="contained"
        color="light"
        // style={{marginLeft:"90px"}}
        // onClick={handleRun}
        // style={{position}}
        style={{left:"95%"}}
      >
        Submit
      </Button>
      <Button
        variant="contained"
        color="light"
        // style={{marginLeft:"90px"}}
        // onClick={handleRun}
        // style={{position}}
        style={{left:"82%"}}
      >
        Run Code
      </Button>
      <Button
        variant="contained"
        color="light"
        // style={{marginLeft:"90px"}}
        // onClick={handleRun}
        // style={{position}}
        style={{left:"35%"}}
      >
        Console
      </Button>
    </div>


  );

}