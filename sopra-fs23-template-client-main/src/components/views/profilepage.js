import {useEffect, useState} from 'react';
import React from 'react';
import {api, handleError} from 'helpers/api';
import {useHistory} from 'react-router-dom';
import {Button} from 'components/ui/Button';
import 'styles/views/Login.scss';
import BaseContainer from "components/ui/BaseContainer";
import PropTypes from "prop-types";
import {Spinner} from 'components/ui/Spinner';
import { useParams } from "react-router";




const FormField = props => {
  return (
    <div className="login field">
      <label className="login label">
        {props.label}
      </label>
      <input
        className="login input"
        placeholder="enter here.."
        type={props.type}
        value={props.value}
        onChange={e => props.onChange(e.target.value)}
      />
    </div>
  );
};


function ProfilePage() {
  const [users, setUsers] = useState(null);
  const [birthdate, setBirthdate] = useState(null);
  const [username, setUsername] = useState(null);
  const history = useHistory();

  // Get ID from URL
  const params = useParams()
  const id = params.token;


  const Player = ({user}) => (
    <div className="player container">
      <div className="player username">Name:{user.username}</div>
      <div className="player id">id: {user.id}</div>
      <div className="player status">Status:{user.status ? "Online" : "Offline"}</div>
      <div className="player created-at">Created at: {user.creationDate}</div>
      {user.birthday && (
        <div className="player birthdate">Birthdate: {user.birthday}</div>
     )}
    </div>
  );
  
 
  Player.propTypes = {
    user: PropTypes.shape({
    username: PropTypes.string.isRequired,
    status: PropTypes.string.isRequired,
    creationDate: PropTypes.string.isRequired,
    birthday: PropTypes.string,
  }).isRequired,
  };
  

  useEffect(() => {
    // effect callbacks are synchronous to prevent race conditions. So we put the async function inside:
    async function fetchData() {
      try {
        const response = await api.get(`/users/${id}`);

        // delays continuous execution of an async operation for 1 second.
        // This is just a fake async call, so that the spinner can be displayed
        // feel free to remove it :)
        await new Promise(resolve => setTimeout(resolve, 1000));

        // Get the returned users and update the state.
        // Find the user with the corresponding ID
        //const currentUser = users.find(user => user.id === id);
        setUsers(response.data);

        // This is just some data for you to see what is available.
        // Feel free to remove it.
        console.log('request to:', response.request.responseURL);
        console.log('status code:', response.status);
        console.log('status text:', response.statusText);
        console.log('requested data:', response.data);

        // See here to get more data.
        console.log(response);
      } catch (error) {
        console.error(`Something went wrong while fetching the users: \n${handleError(error)}`);
        console.error("Details:", error);
        alert("Something went wrong while fetching the users! See the console for details.");
      }
    }

    fetchData();
  }, [parseInt(id)]);


  let content = <Spinner />;

  if (users) {
    content = (
      <div className="game">
        <ul className="game user-list">
          <Player user={users} />
        </ul>
      </div>
    );
  }

  // Get ID from local storage to check if the user in question is logged in
  const storedId = localStorage.getItem('id');

  return (
    <BaseContainer className="game container">
      <h2>Profile page</h2>
      <p className="game paragraph">
        This is the personal profile page of the user you are interested in. <br />
        Information about this user is listed below.  <br />
        Please remember that user-specific changes can only be made from the user account in question.
      </p>
      {content}

      {storedId !== null && parseInt(storedId) === parseInt(id) ? (
      <div className="login container">
        <div className="login form">
          <FormField
            label="Username"
            type="text"
            value={username}
            onChange={(un) => setUsername(un)}
          />
          <FormField
            label="birthdate"
            type="date"
            value={birthdate}
            onChange={(n) => setBirthdate(n)}
          />
          <div className="login button-container">
            <Button width="100%" onClick={() => doChange()}>
              Change
            </Button>
          </div>
        </div>
      </div>
     ) : null}

    <div className="login button-container">
      <Button width="100%" onClick={() => history.goBack()}>
        Back to Overview
      </Button>
    </div>
  </BaseContainer>
);

      


async function doChange() {
  try {
    const requestBody = JSON.stringify({
      username: username,
      birthday: birthdate,
    });
    const response = await api.put(`/users/${id}` , requestBody);

    // Get the returned users and update the state.
    // Find the user with the corresponding ID
    //const currentUser = users.find(user => user

    await new Promise(resolve => setTimeout(resolve, 1000));
    console.log(users)
    const current = {...users}
    current.username = username
    current.birthday = birthdate
    setUsers(current)



    // See here to get more data.
    console.log(response);
  } catch (error) {
    console.error(`Something went wrong while fetching the users: \n${handleError(error)}`);
    console.error("Details:", error);
    alert("Something went wrong while fetching the users! See the console for details.");
  }}



return (
  <BaseContainer>
    <div className="login container">
      <div className="login form">
        <FormField
          label="Username"
          type = "text"
          value={username}
          onChange={un => setUsername(un)}
        />
        <FormField
          label="birthdate"
          type = "date"
          value={birthdate}
          onChange={n => setBirthdate(n)}
        />
        <div className="login button-container">
          <Button
            width="100%"
            onClick={() => doChange()}
          >
            Login
          </Button>
        </div>

        <div className="login button-container">
          <Button
            width="100%"
            onClick={() => history.push(`/register`)}
          >
            Registration-Page
          </Button>
        </div>


      </div>
    </div>
  </BaseContainer>
);
}


export default ProfilePage;