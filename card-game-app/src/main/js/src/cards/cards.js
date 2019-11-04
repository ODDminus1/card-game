import React, {Component} from 'react';
import './cards.css'

let pokerCards = [
  {
    key: "jack",
    cardName: "jack",
    imageUrl: "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Playing_card_spade_J.svg/800px-Playing_card_spade_J.svg.png"
  },
  {
    key: "queen",
    cardName: "queen",
    imageUrl: "https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Playing_card_spade_Q.svg/800px-Playing_card_spade_Q.svg.png"
  },
  {
    key: "king",
    cardName: "king",
    imageUrl: "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9f/Playing_card_spade_K.svg/800px-Playing_card_spade_K.svg.png"
  },
  {
    key: "ace",
    cardName: "ace",
    imageUrl: "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Playing_card_spade_A.svg/800px-Playing_card_spade_A.svg.png"
  }
];

class CardSection extends Component {
  render() {
    const iterator = [0, 1, 2, 3];
    return <div className={"cardList"}>
      {iterator.map(i => {
        var cardProps = {pokerCard: pokerCards[i], magicCard: this.props.cards != null ? this.props.cards[i] : null};
        return <CardTuple cardProps={cardProps}/>
      })}
    </div>;
  }
}

class CardTuple extends Component {
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
    return <div className={[this.state.active ? "cardTupleClicked" : "cardTupleUnclicked", "cardTuple"].join(" ")}
                onClick={this.expand}>
      <PokerCard pokerCard={this.props.cardProps.pokerCard != null ? this.props.cardProps.pokerCard : null}/>
      <CardImage magicCard={this.props.cardProps.magicCard != null ? this.props.cardProps.magicCard : null}/>
    </div>
  }
}

class PokerCard extends Component {
  render() {
    return <div className={"cardContainer pokerCardContainer"}>
      <img className={["cardImage", "pokerCardImage"].join(" ")}
           src={this.props.pokerCard != null ? this.props.pokerCard.imageUrl : null}
           alt={this.props.pokerCard != null ? this.props.pokerCard.cardName : null}
      />
    </div>
  }
}

class CardImage extends Component {
  render() {
    return <div className={"cardContainer magicCardContainer"}>
      <img className={["cardImage", "magicCardImage"].join(" ")}
           src={this.props.magicCard != null ? this.props.magicCard.imageUrl : null}
           alt={this.props.magicCard != null ? this.props.magicCard.cardName : null}
      />
    </div>
  }
}

export default CardSection;
