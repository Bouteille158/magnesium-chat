import { useState } from "react";
import "./App.css";

function App() {
  const [messages, setMessages] = useState<string[]>([]);
  const [message, setMessage] = useState<string>("");

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === "Enter") {
      event.preventDefault(); // Prevents the default action of Enter key press
      if (event.shiftKey) {
        // If Shift + Enter is pressed, send the message
        setMessages([...messages, message]);
        setMessage(""); // Clear the message input field
      } else {
        // If only Enter is pressed, add a new line to the message
        setMessage((prevMessage) => prevMessage + "\n");
      }
    }
  };

  return (
    <>
      <div id="chat-box">
        {messages.map((message, index) => (
          <div key={index}>{message}</div>
        ))}
      </div>
      <input
        type="text"
        id="message"
        name="message"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyDown={handleKeyDown}
      />
      <button
        onClick={() => {
          setMessages([...messages, message]);
          setMessage(""); // Clear the message input field
        }}
      >
        Send
      </button>
    </>
  );
}

export default App;
