import {Button, Grid, MenuItem, Select, TextField, Typography} from "@material-ui/core";
import React, {useEffect, useState} from "react";
import CodeMirror from '@uiw/react-codemirror';
import { javascript } from '@codemirror/lang-javascript';
import {cpp} from '@codemirror/lang-cpp';
import {java} from '@codemirror/lang-java';
import {python} from '@codemirror/lang-python';
import { go } from '@codemirror/legacy-modes/mode/go';
import { StreamLanguage } from '@codemirror/stream-parser';
import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/material.css';
import {Controlled} from 'react-codemirror2';
import {API_URL} from "../../../config/config";
import {useDispatch, useSelector} from "react-redux";
import {authPost} from "../../../api";
import {failed} from "../../../action";
require('codemirror/mode/xml/xml');
require('codemirror/mode/javascript/javascript');

function IDE(){
  const dispatch = useDispatch();
  const [computerLanguage, setComputerLanguage] = useState("CPP");
  const computerLanguageList = ["CPP", "GoLang", "Java", "Python3", "Python2"];
  const [source, setSource] = useState();
  const [screenHeight, setScreenHeight] = useState((window.innerHeight-200) + "px");
  const [input, setInput] = useState("");
  const [output, setOutput] = useState("");
  const token = useSelector((state) => state.auth.token);
  async function handleRun() {
    console.log("input", input);
    console.log("source", source);
    let body = {
      source: source,
      input: input,
    }
    let out = await authPost(dispatch, token, "/ide/"+computerLanguage,body).then(
      (res) =>{
        console.log("res", res);
        setOutput(res.output);
      }
    );


    // await fetch(API_URL + "/ide/"+computerLanguage, {
    //   method: "POST",
    //   headers: {
    //     "content-type": "application/json",
    //     "X-Auth-Token": token,
    //   },
    //   body: JSON.stringify(body),
    // }).then(
    //   (res)=>{
    //     if (!res.ok) {
    //       if (res.status === 401) {
    //         dispatch(failed());
    //         throw Error("Unauthorized");
    //       } else {
    //         console.log("res", res);
    //         try {
    //           res.json().then((res1) => console.log(res1));
    //         } catch (err) {}
    //         throw Error();
    //       }
    //       // return null;
    //     }
    //     console.log("res json", res);
    //   }
    // )
  }
  useEffect(() => {
    let a = "#include<bits/stdc++.h>\n" +
      "using namespace std;\n" +
      "\n" +
      "int main(){\n" +
      "  int a;\n" +
      "  int b;\n" +
      "  cin >> a >> b;\n" +
      "  cout << a+b;\n" +
      "  return 0;\n" +
      "}";
    setSource(a);
    setInput("2 3");
  },[])
  const getExtension = () =>{
    switch (computerLanguage){
      case "CPP":
        return cpp();
      case "GoLang":
        return StreamLanguage.define(go);
      case "Java":
        return java();
      case "Python3":
        return python();
      case "Python2":
        return python();
      default:
        return javascript();
    }
  }

  return(
    <div>

      <TextField
        variant="outlined"
        size="small"
        autoFocus
        // required
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
      <Grid container spacing={12}>
        <Grid item xs={8}>
          <CodeMirror
            value={source}
            height={screenHeight}
            width="100%"
            extensions={getExtension()}
            onChange={(value, viewUpdate) => {
              setSource(value);
            }}
            // theme={"#000000"}
          />
        </Grid>
        <Grid item xs={4}>
          <Grid container spacing={12}>
            <Grid item xs={2}>
            </Grid>
            <Grid item xs={10}>
              <Typography>Input</Typography>
              <CodeMirror
                value={input}
                height={"100px"}
                width="100%"
                extensions={getExtension()}
                onChange={(value, viewUpdate) => {
                  setInput(value);
                }}
                autoFocus={true}
                // theme={"#000000"}
              />
            </Grid>
            <Grid container spacing={12}>
              <Grid item xs={2}></Grid>
              <Grid item xs={10}>
                <Typography>Output</Typography>
                <Controlled
                  value={output}
                  options={{
                    // theme:"#000000",
                    lineNumbers: true
                  }}
                />
              </Grid>
              <Grid item xs={2}></Grid>
              <Button
                variant="contained"
                color="primary"
                style={{marginLeft:"45px"}}
                onClick={handleRun}
              >
                Run
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </div>




  );
}

export default IDE;