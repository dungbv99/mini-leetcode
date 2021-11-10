import {useEffect, useState} from "react";
import {request} from "./Request";
import {useHistory, useParams} from "react-router-dom";
import Box from "@mui/material/Box";
import Typography from "@material-ui/core/Typography";
import PropTypes from "prop-types";
import {Tab, Tabs} from "@material-ui/core";
import * as React from 'react';

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
  const history = useHistory();

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  useEffect(() =>{
    request(
      "get",
      "/get-contest-detail/"+contestId,
      (res) =>{
        console.log("res ", res.data);
        setContestTime(res.data.contestTime);
        setContestName(res.data.contestName);
        setProblems(res.data.list);
      }
    ).then();
  }, [])

  return (
    <Box
      sx={{ flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height:window.innerHeight - 100 }}
    >

      <Tabs
        orientation="vertical"
        variant="scrollable"
        value={value}
        onChange={handleChange}
        indicatorColor={"primary"}
        aria-label="Vertical tabs example"
        sx={{ borderRight: 1, borderColor: 'divider', width: 50 }}
      >
        <Tab label="ALL" {...a11yProps(0)} style={{minWidth: 50}}/>
        {

          problems.map((problem, index) =>{
            return(
              <Tab label={index+1} {...a11yProps(problem.problemId)} style={{minWidth: 50}}/>
            );

          })
        }

      </Tabs>

      <TabPanel value={value} index={0}>
        Item Three
      </TabPanel>

      {problems.map((problem, index)=>{
        return(
          <TabPanel value={value} index={index+1}>
            {problem.problemId}
          </TabPanel>
        )
      })}

    </Box>
  );
}