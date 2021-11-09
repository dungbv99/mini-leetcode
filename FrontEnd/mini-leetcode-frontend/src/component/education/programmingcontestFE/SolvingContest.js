import {useEffect} from "react";
import {request} from "./Request";
import {useParams} from "react-router-dom";

export default function SolvingContest(){
  const {contestId} = useParams();
  useEffect(() =>{
    request(
      "get",
      "/get-contest-detail/"+contestId,
      (res) =>{
        console.log("res ", res);
      }
    ).then();
  })
  return(
    <div>
      solve contest
    </div>
  )
}