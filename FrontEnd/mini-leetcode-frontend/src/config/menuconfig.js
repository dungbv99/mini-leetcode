import DashboardRoundedIcon from "@material-ui/icons/DashboardRounded";
import EventNoteIcon from "@material-ui/icons/EventNote";
import LocalLibraryIcon from "@material-ui/icons/LocalLibrary";
import React from "react";
import { GiTeacher } from "react-icons/gi";
import TeachingIcon from "../assets/icons/mathematics.svg";
import { buildMapPathMenu } from "../utils/MenuUtils";
import { general } from "./menuconfig/general";
import {ProgrammingContestMenuStudent, ProgrammingContestMenuTeacher} from "./menuconfig/ProgramingContest";
import {user} from "./menuconfig/user";
import PeopleIcon from "@material-ui/icons/People";
import PersonIcon from "@material-ui/icons/Person";

export const MENU_LIST = [];
MENU_LIST.push(general);
MENU_LIST.push(ProgrammingContestMenuTeacher);
MENU_LIST.push(user);
MENU_LIST.push(ProgrammingContestMenuStudent);

export const menuIconMap = new Map();
menuIconMap.set(
  "Schedule",
  <EventNoteIcon />
  //   <img alt="Task Schedule icon" src={TaskScheduleIcon} height={24} width={24} />
);
menuIconMap.set(
  "Teaching",
  <img alt="Teaching icon" src={TeachingIcon} height={24} width={24} />
);
menuIconMap.set("DashboardIcon", <DashboardRoundedIcon />);
menuIconMap.set("GiTeacher", <GiTeacher size={24} />);
menuIconMap.set("LocalLibraryIcon", <LocalLibraryIcon size={24}/>);
menuIconMap.set("PeopleIcon", <PeopleIcon size={24}/>);
menuIconMap.set("LocalLibraryIcon", <LocalLibraryIcon size={24}/>);
menuIconMap.set("PersonIcon", <PersonIcon />);



export const mapPathMenu = buildMapPathMenu(MENU_LIST);
