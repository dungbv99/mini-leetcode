import React from "react";
import { Route, Switch, useRouteMatch } from "react-router";
import Approve from "../views/UserRegister/Approve";

export default function UserRoute() {
  let { path } = useRouteMatch();
  return (
    <div>
      <Switch>
        <Route component={Approve} path={`${path}/user/approve-register`} />
      </Switch>
    </div>
  );
}
