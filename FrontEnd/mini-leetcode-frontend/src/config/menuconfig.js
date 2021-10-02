import DashboardRoundedIcon from "@material-ui/icons/DashboardRounded";
import EventNoteIcon from "@material-ui/icons/EventNote";
import LocalLibraryIcon from "@material-ui/icons/LocalLibrary";
import React from "react";
import { GiTeacher } from "react-icons/gi";
import TeachingIcon from "../assets/icons/mathematics.svg";
import { buildMapPathMenu } from "../utils/MenuUtils";
import { eduLearningManagement } from "./menuconfig/classmanagement/student";
import { eduTeachingManagement } from "./menuconfig/classmanagement/teacher";
import { general } from "./menuconfig/general";
import { user } from "./menuconfig/user";


export const MENU_LIST = [];
MENU_LIST.push(general);
MENU_LIST.push(eduTeachingManagement);
MENU_LIST.push(eduLearningManagement);


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
menuIconMap.set("LocalLibraryIcon", <LocalLibraryIcon />);

export const mapPathMenu = buildMapPathMenu(MENU_LIST);
