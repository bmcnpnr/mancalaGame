import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import { getStompClient, sendMessage } from './WebSocketManager';

let stompClient;

const styles = theme => ({
  row: {
    flexGrow: 1,
  },

  paper: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
    pointerEvents: 'none',
  },
});

const useStyles = makeStyles(theme => ({
  row: {
    flexGrow: 1,
  },

  paper: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
    pointerEvents: 'none',
  },
}));

function GameRows(props) {
  const classes = useStyles();
  return (
    <div className={classes.row}>
      <Grid container spacing={3}>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player1" || props.playerOfThisClient === "player1"}
            onClick={() => props.handlePlayerMove(0, 0)}>{props.gameTable[0][0]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player1" || props.playerOfThisClient === "player1"}
            onClick={() => props.handlePlayerMove(0, 1)}>{props.gameTable[0][1]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player1" || props.playerOfThisClient === "player1"}
            onClick={() => props.handlePlayerMove(0, 2)}>{props.gameTable[0][2]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player1" || props.playerOfThisClient === "player1"}
            onClick={() => props.handlePlayerMove(0, 3)}>{props.gameTable[0][3]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player1" || props.playerOfThisClient === "player1"}
            onClick={() => props.handlePlayerMove(0, 4)}>{props.gameTable[0][4]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player1" || props.playerOfThisClient === "player1"}
            onClick={() => props.handlePlayerMove(0, 5)}>{props.gameTable[0][5]}
          </Button>
        </Grid>
      </Grid>
      <Grid container spacing={3}>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player2" || props.playerOfThisClient === "player2"}
            onClick={() => props.handlePlayerMove(1, 0)}>{props.gameTable[1][0]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player2" || props.playerOfThisClient === "player2"}
            onClick={() => props.handlePlayerMove(1, 1)}>{props.gameTable[1][1]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player2" || props.playerOfThisClient === "player2"}
            onClick={() => props.handlePlayerMove(1, 2)}>{props.gameTable[1][2]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player2" || props.playerOfThisClient === "player2"}
            onClick={() => props.handlePlayerMove(1, 3)}>{props.gameTable[1][3]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player2" || props.playerOfThisClient === "player2"}
            onClick={() => props.handlePlayerMove(1, 4)}>{props.gameTable[1][4]}
          </Button>
        </Grid>
        <Grid item xs>
          <Button disabled={props.nextPlayerToPlay === "player2" || props.playerOfThisClient === "player2"}
            onClick={() => props.handlePlayerMove(1, 5)}>{props.gameTable[1][5]}
          </Button>
        </Grid>
      </Grid>
    </div>
  );
}

class MancalaGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      gameTable: [[6, 6, 6, 6, 6, 6], [6, 6, 6, 6, 6, 6]],
      playerOfThisClient: null,
      nextPlayerToPlay: null,
      playerOneScore: 0,
      playerTwoScore: 0
    };
    this.handlePlayerMove = this.handlePlayerMove.bind(this);

  }
  handlePlayerMove(row, col) {
    sendMessage(JSON.stringify({ 'row': row, 'col': col }));
  }

  componentDidMount() {
    stompClient = getStompClient();
    stompClient.connect({}, function (frame) {
      stompClient.subscribe('/topic/mancala-notifications', function (message) {
        const jsonObj = JSON.parse(message.body);
        console.log(message.body);

        this.setState({
          gameTable: jsonObj.table,
          playerOfThisClient: this.state.playerOfThisClient == null ? jsonObj.playerOfThisClient : this.state.playerOfThisClient,
          nextPlayerToPlay: jsonObj.nextPlayerToPlay,
          playerOneScore: jsonObj.userOneScore,
          playerTwoScore: jsonObj.userTwoScore
        });
      }.bind(this));
      stompClient.send("/mancalaGame/connectToGame");
    }.bind(this));
  }
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <Grid container spacing={3} direction={'row'}>
          <Grid item xs>
            <Paper className={classes.paper}>{this.state.playerOneScore}</Paper>
          </Grid>
          <Grid item xs>
            <GameRows handlePlayerMove={this.handlePlayerMove}
              gameTable={this.state.gameTable}
              nextPlayerToPlay={this.state.nextPlayerToPlay}
              playerOfThisClient={this.state.playerOfThisClient} />
          </Grid>
          <Grid item xs>
            <Paper className={classes.paper}>{this.state.playerTwoScore}</Paper>
          </Grid>
        </Grid>
      </div>
    );
  }

}

MancalaGame.propTypes = {
  classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(MancalaGame);
