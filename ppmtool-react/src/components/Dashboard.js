import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";
import CreateButton from "./Project/CreateButton";
import { connect } from "react-redux";
import { getProjects } from "../actions/ProjectActions";
import PropTypes from "prop-types";

class Dashboard extends Component {
  //lifecycle hook
  componentDidMount() {
    this.props.getProjects();
  }

  render() {
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <br />
              <CreateButton />
              <br />
              <hr />
              <ProjectItem />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  project: PropTypes.object.isRequired,
  getProjects: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  project: state.project
});

export default connect(mapStateToProps, { getProjects })(Dashboard);
