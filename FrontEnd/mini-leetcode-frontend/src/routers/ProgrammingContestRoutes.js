import { Route, Switch, useRouteMatch } from "react-router";
import ListProblem from "../component/education/programmingcontestFE/ListProblem";
import CreateProblem from "../component/education/programmingcontestFE/CreateProblem";
import IDE from "../component/education/programmingcontestFE/IDE";

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
          component={IDE}
          path={`${path}/ide`}
          />
      </Switch>
    </div>
  )
}