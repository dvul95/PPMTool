import React, { Component } from "react";
import ProjectTask from "./ProjectTasks/ProjectTask";

class Backlog extends Component {
  render() {
    const { project_tasks } = this.props;

    const tasks = project_tasks.map((project_task) => (
      <ProjectTask key={project_task.id} project_task={project_task} />
    ));

    //list of task Items

    let todoList = [];
    let inprogressList = [];
    let doneList = [];

    for (let i = 0; i < tasks.length; i++) {
      //console.log(tasks[i]);

      //TODO items
      if (tasks[i].props.project_task.status === "TODO") {
        todoList.push(tasks[i]);
      }
      //In progress items
      if (tasks[i].props.project_task.status === "IN_PROGRESS") {
        inprogressList.push(tasks[i]);
      }
      //done items
      if (tasks[i].props.project_task.status === "DONE") {
        doneList.push(tasks[i]);
      }
    }

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {todoList}
            {/**  */}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {inprogressList}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {doneList}
          </div>
        </div>
      </div>
    );
  }
}

export default Backlog;
