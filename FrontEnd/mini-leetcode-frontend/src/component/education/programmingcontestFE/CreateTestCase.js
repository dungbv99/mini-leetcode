import {Box, MenuItem, Tab, Tabs, TextField, Toolbar} from "@material-ui/core";
import * as React from "react";
import {useState} from "react";
import Typography from "@mui/material/Typography";
import PropTypes from "prop-types";
import {ScrollBox} from "react-scroll-box";
import {a11yProps, TabPanel} from "./TabPanel";


export default function CreateTestCase(){
  const [value, setValue] = useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  return(
    <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
      <Toolbar style={{height:"0px", marginTop:"-12px", marginBottom:"-8px", border:"1px solid transparent", position: "relative", width:"100%"}} color={"default"} >
        <Tabs
          value={value}
          onChange={handleChange}
          indicatorColor={"primary"}
          autoFocus
          style={{
            width:"50%",
            display:"inline-table",
            border: "1px solid transparent ",
            position: "relative",
            borderBottom:"none",
          }}
          // variant={"fullWidth"}
          aria-label="basic tabs example"
        >
          <Tab label="Description" {...a11yProps(0)} style={{width:"25%"}}/>
          <Tab label="Solution" {...a11yProps(1)} style={{width:"25%"}}/>
          <Tab label="Discuss" {...a11yProps(2)} style={{width:"25%"}}/>
          <Tab label="Submissions" {...a11yProps(3)} style={{width:"25%"}}/>
        </Tabs>
      </Toolbar>
      <TabPanel value={value} index={0}>
        <ScrollBox style={{width: '100%', overflow:"auto", height:(window.innerHeight-180) + "px"}}>
          <Typography variant={"h4"} color={"#d6d6d6"}  >

          </Typography>
        </ScrollBox>

      </TabPanel>
      <TabPanel value={value} index={1}>
      </TabPanel>
      <TabPanel value={value} index={2}>
      </TabPanel>
      <TabPanel value={value} index={3}>
      </TabPanel>
    </Box>
  );
}