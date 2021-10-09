import * as React from 'react';
import Tab from "@material-ui/core/Tab";
import Tabs from "@material-ui/core/Tabs";
import {AppBar, Box, Typography} from "@material-ui/core";
import PropTypes from "prop-types";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      // role="tabpanel"
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
TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,

  };
}





export default function ProblemDetail(){
  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  return (
    <div>
      <AppBar position="static" color={"default"} variant={"elevation"} style={{width: "50%"}}>
        <Tabs
          value={value}
          onChange={handleChange}
          aria-label="simple tabs example"
          indicatorColor={"primary"}
        >

          <Tab label="Description" color={"while"} {...a11yProps(0)} style={{width: "25%"}}/>
          <Tab label="Solution" color={"while"} {...a11yProps(1)} style={{width: "25%"}}/>
          <Tab label="Discuss" color={"while"} {...a11yProps(2)} style={{width: "25%"}}/>
          <Tab label="Submissions" color={"while"} {...a11yProps(3)} style={{width: "25%"}}/>

        </Tabs>
      </AppBar>
      <TabPanel value={value} index={0}>
          0
      </TabPanel>
      <TabPanel value={value} index={1}>
        1
      </TabPanel>
      <TabPanel value={value} index={2}>
        2
      </TabPanel>
      <TabPanel value={value} index={3}>
        3
      </TabPanel>
    </div>
  );

}