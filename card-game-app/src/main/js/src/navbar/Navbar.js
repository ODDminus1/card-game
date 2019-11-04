import React, {Component} from 'react';
import './Navbar.css'
import '../cards/cards'

class Navbar extends Component {
  render() {
    console.log(this.props)
    return (
      <div className={"navbar"}>
        <HomeButton refresher={this.props.refresher}/>
      </div>
    );
  }
}

class HomeButton extends Component {
  render() {
    console.log(this.props)
    return <button type={"button"} className={"refreshButton"} onClick={() => this.props.refresher()}>Refresh</button>
  }
}

export default Navbar;