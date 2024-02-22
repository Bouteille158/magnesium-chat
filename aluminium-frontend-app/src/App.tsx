import { useState } from "react";
import "./App.css";

function App() {
  const [messages, setMessages] = useState<string[]>([]);

  return (
    <>
      <div id="chat-box">
        {messages.map((message, index) => (
          <div key={index}>{message}</div>
        ))}
      </div>
      <input type="text" id="message" name="message" />
      <button
        onClick={() => {
          const message = (
            document.getElementById("message") as HTMLInputElement
          ).value;
          setMessages([...messages, message]);
        }}
      >
        Send
      </button>
    </>
  );
}

export default App;
