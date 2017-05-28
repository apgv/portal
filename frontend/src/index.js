import "bootstrap/dist/css/bootstrap.css";
import React from "react";
import ReactDOM from "react-dom";
import registerServiceWorker from "./registerServiceWorker";
import "./index.css";
import {makeMainRoutes} from "./routes";

(() => {
    fetch('/auth0/config')
        .then(response => response.json())
        .then(auth0Config => {
            const routes = makeMainRoutes(auth0Config);

            ReactDOM.render(routes, document.getElementById('root'));
            registerServiceWorker();
        })
        .catch(error => console.log(error));
})();