import "./App.scss";
import ChatWindow from "./components/chat/ChatWindow";
import { StompSessionProvider } from "react-stomp-hooks";
import { useEffect, useState } from "react";
import {
  askNotificationPermission,
  subscribeToPushNotifications,
} from "./services/notification";
import Header from "./components/Header";
import Cookies from "js-cookie";
import axios from "axios";

const websocketURL = `${import.meta.env.VITE_SODIUM_API_URL}/socket`;

function App() {
  const [isNotificationPermissionGranted, setIsNotificationPermissionGranted] =
    useState<boolean>(false);
  const [token, _] = useState<string | undefined>(Cookies.get("token"));

  useEffect(() => {
    console.log("useEffect for askNotificationPermission");
    const status = askNotificationPermission();
    status.then((permission) => {
      console.log("permission status: ", permission);
      setIsNotificationPermissionGranted(permission);
    });
    console.log("token", token);
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  }, []);

  useEffect(() => {
    if (!isNotificationPermissionGranted) {
      return;
    }
    console.log(
      "isNotificationPermissionGranted: ",
      isNotificationPermissionGranted
    );
    subscribeToPushNotifications();
  }, [isNotificationPermissionGranted]);

  console.log("websocketURL: ", websocketURL);

  return (
    <StompSessionProvider
      url={websocketURL}
      onConnect={() => {
        console.log("Connected to the WebSocket server");
      }}
    >
      <div className="App">
        <Header title="Welcome to chat" />
        <ChatWindow />
      </div>
    </StompSessionProvider>
  );
}

export default App;
