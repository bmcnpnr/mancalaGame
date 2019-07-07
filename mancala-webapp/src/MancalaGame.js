import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';

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
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
      </Grid>
      <Grid container spacing={3}>
        <Grid item xs>
          <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
        <Grid item xs>
        <Button onClick={props.handlePlayerMove}>6</Button>
        </Grid>
      </Grid>
    </div>
  );
}

class MancalaGame extends React.Component {
  constructor(props) {
    super(props);
    this.handlePlayerMove = this.handlePlayerMove.bind(this);
  }
  handlePlayerMove(e) {
    console.log(e);
  }
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <Grid container spacing={3} direction={'row'}>
          <Grid item xs>
            <Paper className={classes.paper}>6</Paper>
          </Grid>
          <Grid item xs>
            <GameRows handlePlayerMove={this.handlePlayerMove} />
          </Grid>
          <Grid item xs>
            <Paper className={classes.paper}>6</Paper>
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
