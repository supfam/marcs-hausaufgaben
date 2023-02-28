import React, {useState} from 'react';
import {api, handleError} from 'helpers/api';
import User from 'models/User';
import {useHistory} from 'react-router-dom';
import {Button} from 'components/ui/Button';
import 'styles/views/Login.scss';
import BaseContainer from "components/ui/BaseContainer";
import PropTypes from "prop-types";

/*
It is possible to add multiple components inside a single file,
however be sure not to clutter your files with an endless amount!
As a rule of thumb, use one file per component and only add small,
specific components that belong to the main one in the same file.
 */
const FormField = props => {
  return (
    <div className="regiser field">
      <label className="regiser label">
        {props.label}
      </label>
      <input
        className="regiser input"
        placeholder="enter here.."
        value={props.value}
        type={props.type}
        onChange={e => props.onChange(e.target.value)}
      />
    </div>
  );
};

FormField.propTypes = {
  type: PropTypes.string,
  label: PropTypes.string,
  value: PropTypes.string,
  onChange: PropTypes.func
};

const Registration = props => {
  const history = useHistory();
  const [password, setPassword] = useState(null);
  const [username, setUsername] = useState(null);
  const [creationDate, setCreationDate] = useState(null);

  const doRegistration = async () => {
    try {
      const requestBody = JSON.stringify({username, password});

      // Get the current date and time.
      const currentDate  = new Date();
      setCreationDate(currentDate.toISOString());

      const response = await api.post('/users', requestBody);
      
      // Get the returned user and update a new object.
      const user = new User(response.data, creationDate);

      // Store the token into the local storage.
      localStorage.setItem('token', user.token);

      // Login successfully worked --> navigate to the route /game in the GameRouter
      history.push(`/game`);
    } catch (error) {
      alert(`Something went wrong during the registration: \n${handleError(error)}`);
    }
  };

  return (
    <BaseContainer>
      <div className="login container">
        <div className="login form">
          <FormField
            label="enter username"
            type = "text"
            value={username}
            onChange={un => setUsername(un)}
          />
          <FormField
            label="set password"
            type = "password"
            value={password}
            onChange={n => setPassword(n)}
          />
          <div className="login button-container">
            <Button
              disabled={!username || !password}
              width="100%"
              onClick={() => doRegistration()}
            >
              Registrate now
            </Button>
          </div>

          <div className="login button-container">
            <Button
              
              width="100%"
              onClick={() => history.push(`/login`)}
            >
              Back to Login-Screen
            </Button>
          </div>


        </div>
      </div>
    </BaseContainer>
  );
};

/**
 * You can get access to the history object's properties via the withRouter.
 * withRouter will pass updated match, location, and history props to the wrapped component whenever it renders.
 */
export default Registration;
