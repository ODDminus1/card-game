import React, {Component} from 'react';
import ReactDOM from 'react-dom'
import './cards.css'

let pokerCards = [
  {key: "jack", cardName: "jack", imageUrl: "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Playing_card_spade_J.svg/800px-Playing_card_spade_J.svg.png"},
  {key: "queen", cardName: "queen", imageUrl: "https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Playing_card_spade_Q.svg/800px-Playing_card_spade_Q.svg.png"},
  {key: "king", cardName: "king", imageUrl: "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9f/Playing_card_spade_K.svg/800px-Playing_card_spade_K.svg.png"},
  {key: "ace", cardName: "ace", imageUrl: "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Playing_card_spade_A.svg/800px-Playing_card_spade_A.svg.png"}
];

class CardSection extends Component {
  state = {cards: []};

  componentDidMount() {
    // constructor() {
    let types = "instant,sorcery";
    let count = 4;
    console.log("setting state.")
    fetch('http://localhost:8080/api/cards?numberOfCards=' + count + '&types=' + types)
      .then(res => res.json())
      .then((data) => {
        this.setState({cards: data, className: "card"});
      })
  }

  render() {
    var iterator = [0, 1, 2, 3];

    return React.createElement('ul', {className: "cardList"},
      iterator.map(i => {
          var cardProps = {pokerCard: pokerCards[i], magicCard: this.state.cards[i]};
          return <Card cardProps={cardProps}/>
        }
      )
    );
  }
}

class Card extends Component {
  constructor(props) {
    super(props);
    this.expand = this.expand.bind(this);
    this.state = {active: false};
  }

  expand = () => {
    const currentState = this.state.active;
    this.setState({active: !currentState});
  };

  render() {
    return <li className={this.state.active ? "expandedCardElement" : "cardElement"}
               onClick={this.expand}
    >
      <PokerCard imageUrl={this.props.cardProps.pokerCard != null ? this.props.cardProps.pokerCard.imageUrl : null}/>
      <CardImage imageUrl={this.props.cardProps.magicCard != null ? this.props.cardProps.magicCard.imageUrl : null}/>
    </li>
  }
}

class PokerCard extends Component {
  render() {
    console.log(this.props);
    return <img className={["cardImage", "pokerCardImage"].join(" ")} src={this.props.imageUrl != null ? this.props.imageUrl : null}/>
  }
}

class CardImage extends Component {
  render() {
    return <img className={["cardImage", "magicCardImage"].join(" ")} src={this.props.imageUrl != null ? this.props.imageUrl : null}/>
  }
}

ReactDOM.render(
  <CardSection/>,
  document.getElementById('root')
);