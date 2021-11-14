import {styled} from "@mui/material/styles";
import {TableCell} from "@material-ui/core";
import {tableCellClasses} from "@mui/material/TableCell";
import TableRow from "@material-ui/core/TableRow";
import {cppLanguage} from "@codemirror/lang-cpp";
import {StreamLanguage} from "@codemirror/stream-parser";
import {go} from "@codemirror/legacy-modes/mode/go";
import {java} from "@codemirror/lang-java";
import {pythonLanguage} from "@codemirror/lang-python";
import {javascript} from "@codemirror/lang-javascript";

export default function lib() {
  return ;
}

export function sleep(ms){
  return new Promise(resolve => setTimeout(resolve, ms));
}

export  function getColorLevel(level){
  // const colors = ['red', 'yellow', 'green']
  switch (level){
    case 'easy':
      return 'green';
    case 'medium':
      return 'orange';
    case 'hard':
      return 'red';
    default:
      return 'blue';
  }
}

export const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

export const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

export const getExtension = (computerLanguage) =>{
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

export const getStatusColor = (status) => {
  switch (status){
    case 'Accept':
      return 'green';
    default:
      return 'red';
  }
}