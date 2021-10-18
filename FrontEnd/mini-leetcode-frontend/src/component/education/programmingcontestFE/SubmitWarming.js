import {Alert} from "@material-ui/lab";
import * as React from "react";

export function SubmitWarming(props){
  const showSubmitWarming=props.showSubmitWarming;
  if(!showSubmitWarming){
    return(<div></div>);
  }else {
    return (
      <div>
        <Alert severity="error">Your source must be pass compile process</Alert>
      </div>
    );
  }
}