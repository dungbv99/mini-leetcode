import ContentLoader from "react-content-loader";
import * as React from "react";
import {Alert} from "@material-ui/lab";
import Box from "@mui/material/Box";
import {ScrollBox} from "react-scroll-box";
import Typography from "@mui/material/Typography";
import {TableBody, TableCell, TableHead} from "@material-ui/core";
import TableRow from "@material-ui/core/TableRow";
import {Link} from "react-router-dom";

export function ProblemSubmission(props) {
  const submitted = props.submitted;
  const problemSubmission = props.problemSubmission;

  if(!submitted){

    return(
      <div>
        <br/><br/><br/>
        <Box sx={{ display: 'flex',   p: 1}}>

          <ScrollBox style={{width: '100%', overflow:"auto", height:"148px"}}  >
            <Typography variant={"h4"} style={{marginLeft:"20%"}} color={"#d6d6d6"}  >
              You don't have any submissions yet.
            </Typography>
          </ScrollBox>
        </Box>

      </div>
    );
  }else{
    return (
      <div>
        <TableHead>
          <TableCell width={"15%"}>
            Time Submitted
          </TableCell>
          <TableCell width={"15%"}>
            Status
          </TableCell>
          <TableCell width={"15%"}>
            Run time
          </TableCell>
          <TableCell width={"15%"}>
            Memory
          </TableCell>
          <TableCell width={"15%"}>
            Language

          </TableCell>
          <TableCell width={"15%"}>
            Point
          </TableCell>
        </TableHead>
        <TableBody>
          {
            problemSubmission.map(p =>{
              return(
                <TableRow>
                  <TableCell width={"15%"}>
                    {p.timeSubmitted}
                  </TableCell>
                  <TableCell width={"15%"}>
                    {p.status}
                  </TableCell>
                  <TableCell width={"15%"}>
                    {p.runTime}
                  </TableCell>
                  <TableCell width={"15%"} >
                    {p.memory}
                  </TableCell>
                  <TableCell width={"15%"}>
                    {p.language}
                  </TableCell>
                  <TableCell width={"15%"}>
                    {p.point}
                  </TableCell>
                </TableRow>
              );
            })
          }
        </TableBody>
      </div>
    )
  }
}
