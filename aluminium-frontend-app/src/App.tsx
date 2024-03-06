import "./App.css";
import ChatWindow from "./components/chat/ChatWindow";
import { StompSessionProvider } from "react-stomp-hooks";

const websocketURL = `${import.meta.env.VITE_SODIUM_API_URL}/socket`;

function App() {
  console.log("websocketURL: ", websocketURL);
  return (
    <>
      <StompSessionProvider url={websocketURL}>
        <ChatWindow />
      </StompSessionProvider>
    </>
  );
}

export default App;
