export const ProgrammingContestMenuTeacher = {
  id: "MENU_TEACHER",
  path: "",
  isPublic: false,
  icon: "LocalLibraryIcon",
  text: "Programming Contest Teacher",
  child: [
    {
      id: "MENU_TEACHER",
      path: "/programming-contest/list-problems",
      isPublic: false,
      icon: null,
      text: "Problem",
      child: [],
    },
    {
      id: "MENU_TEACHER",
      path: "/programming-contest/create-problem",
      isPublic: false,
      icon: null,
      text: "Create Problem",
      child: [],
    },
    {
      id: "MENU_TEACHER",
      path: "/programming-contest/create-contest",
      isPublic: false,
      icon: null,
      text: "Create Contest",
      child: [],
    },
    // {
    //   id: "MENU_TEACHER",
    //   path: "/programming-contest/list-contest",
    //   isPublic: false,
    //   icon: null,
    //   text: "List Contest",
    //   child: [],
    // },
    {
      id: "MENU_TEACHER",
      path: "/programming-contest/ide",
      isPublic: false,
      icon: null,
      text: "IDE",
      child: [],
    },
    {
      id: "MENU_TEACHER",
      path: "/programming-contest/teacher-list-contest-manager",
      isPublic: false,
      icon: null,
      text: "List Contest Manager",
      child: [],
    },
  ],
};

export const ProgrammingContestMenuStudent = {
  id: "MENU_STUDENT",
  path: "",
  isPublic: false,
  icon: "LocalLibraryIcon",
  text: "Programming Contest Student",
  child: [
    {
      id: "MENU_STUDENT",
      path: "/programming-contest/student-list-contest-not-registered",
      isPublic: false,
      icon: null,
      text: "List Contest Not Registered",
      child: [],
    },
    {
      id: "MENU_STUDENT",
      path: "/programming-contest/student-list-contest-registered",
      isPublic: false,
      icon: null,
      text: "List Contest Registered",
      child: [],
    },
    {
      id: "MENU_STUDENT",
      path: "/student/ide1",
      isPublic: false,
      icon: null,
      text: "IDE",
      child: [],
    },
    {
      id: "MENU_STUDENT",
      path: "/programming-contest/student-public-problem",
      isPublic: false,
      icon: null,
      text: "Practical Problem",
      child: [],
    },
  ],
}
