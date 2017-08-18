import React from "react";
import ReactDOM from "react-dom";
import registerServiceWorker from "./registerServiceWorker";
import "./index.css";
import WebFontLoader from "webfontloader";
import App from "./App";

WebFontLoader.load({
    google: {
        families: ['Roboto:300,400,500,700', 'Material Icons'],
    },
});

(() => {
    fetch('/auth0/config')
        .then(response => response.json())
        .then(auth0Config => {
            ReactDOM.render(<App auth0Config={auth0Config}/>, document.getElementById('root'));
            registerServiceWorker();
        })
        .catch(error => console.log(error));
})();