import React from "react";
import ReactDOM from "react-dom";
import registerServiceWorker from "./registerServiceWorker";
import "./index.css";
import App from "./App";

(() => {
    fetch('/auth0/config')
        .then(response => response.json())
        .then(auth0Config => {
            ReactDOM.render(<App auth0Config={auth0Config}/>, document.getElementById('root'));
            registerServiceWorker();
        })
        .catch(error => console.log(error));
})();