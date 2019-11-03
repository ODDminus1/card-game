import React, {Component} from 'react';
import ReactDOM from 'react-dom'
import './index.css'
import './cards/cards'

class Table extends Component {
  state = {
    number: 0
  };

  refreshState() {
    fetch('http://localhost:8080/api/random')
      .then(res => res.json())
      .then((data) => {
        this.setState({number: data})
      })
  }

  componentDidMount() {
    this.refreshState();
  }

  renderListElement(i) {
    var props = {value: this.state.number};
    return React.createElement(ListElement, props, props.value.number)
  }

  render() {
    return React.createElement('div', {className: 'shopping-list'},
      React.createElement('h1', null, 'Random number'),
      React.createElement('ul', null,
        this.renderListElement(1),
        this.renderListElement(2)
      ));
  }
}

class ListElement extends Component {
  render() {
    return React.createElement('li', null, this.props.value)
  }
}

if (false) {
  ReactDOM.render(
    <Table/>,
    document.getElementById('root')
  );
}