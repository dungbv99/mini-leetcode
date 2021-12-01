import { Route, Switch, useRouteMatch } from "react-router";
import ListProblem from "../component/education/programmingcontestFE/ListProblem";
import CreateProblem from "../component/education/programmingcontestFE/CreateProblem";
import IDE from "../component/education/programmingcontestFE/IDE";
import ProblemDetail from "../component/education/programmingcontestFE/ProblemDetail";
import CreateTestCase from "../component/education/programmingcontestFE/CreateTestCase";
import EditProblem from "../component/education/programmingcontestFE/EditProblem";
import ProblemSubmissionDetail from "../component/education/programmingcontestFE/ProblemSubmissionDetail";
import CreateContest from "../component/education/programmingcontestFE/CreateContest";
import ListContest from "../component/education/programmingcontestFE/ListContest";
import SolvingContest from "../component/education/programmingcontestFE/SolvingContest";
import {StudentContest} from "../component/education/programmingcontestFE/StudentContest";
import {ListContestManager} from "../component/education/programmingcontestFE/ListContestManager";

export default function ProgrammingContestRoutes(){
  let { path } = useRouteMatch();
  return(
    <div>
      <Switch>
        <Route
          component={ListProblem}
          path={`${path}/list-problems`}
        />
        <Route
          component={CreateProblem}
          path={`${path}/create-problem`}
          />
        <Route
          component={EditProblem}
          path={`${path}/edit-problem/:problemId`}/>
        <Route
          component={IDE}
          path={`${path}/ide`}
          />
        <Route
          component={ProblemDetail}
          path={`${path}/problem-detail/:problemId`}
        />
        <Route
          component={CreateTestCase}
          path={`${path}/problem-detail-create-test-case/:problemId`}
          />
        <Route
          component={ProblemSubmissionDetail}
          path={`${path}/problem-submission-detail/:problemSubmissionId`}
        />
        <Route
          component={CreateContest}
          path={`${path}/create-contest`}
          />
        <Route
          component={ListContest}
          path={`${path}/list-contest`}
        />
        <Route
          component={SolvingContest}
          path={`${path}/solving-contest/:contestId`}
        />
        <Route
          component={StudentContest}
          path={`${path}/student-list-contest`}
          />
        <Route
          component={ListContestManager}
          path={`${path}/list-contest-manager`}
          />
      </Switch>
    </div>
  )
}