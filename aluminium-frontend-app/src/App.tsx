import "./App.scss";
import ChatWindow from "./components/chat/ChatWindow";
import { StompSessionProvider } from "react-stomp-hooks";
import { useEffect, useState } from "react";
import {
  askNotificationPermission,
  subscribeToPushNotifications,
} from "./services/notification";
import Header from "./components/Header";

const websocketURL = `${import.meta.env.VITE_SODIUM_API_URL}/socket`;

function App() {
  const [isNotificationPermissionGranted, setIsNotificationPermissionGranted] =
    useState<boolean>(false);

  useEffect(() => {
    console.log("useEffect for askNotificationPermission");
    const status = askNotificationPermission();
    status.then((permission) => {
      console.log("permission status: ", permission);
      setIsNotificationPermissionGranted(permission);
    });
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
    <StompSessionProvider url={websocketURL}>
      <div className="App">
        <Header title="Welcome to chat" />
        <ChatWindow />
      </div>
    </StompSessionProvider>
  );
}

export default App;
