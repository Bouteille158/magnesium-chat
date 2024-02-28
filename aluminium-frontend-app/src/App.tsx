import { useEffect, useRef, useState } from "react";
import "./App.css";
import Spacer from "./components/Spacer";
import { Message } from "./types/Message";

function App() {
  const [messages, setMessages] = useState<Message[]>([]);
  const [message, setMessage] = useState<string>("");

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === "Enter") {
      event.preventDefault(); // Prevents the default action of Enter key press
      if (event.shiftKey) {
        // If Shift + Enter is pressed, add a new line to the message
        setMessage((prevMessage) => prevMessage + "\n");
      } else {
        // If message is empty, do nothing
        if (message.trim() === "") {
          return;
        }
        // TODO: Add author to the message
        let newMessage: Message = { text: message, author: "user" };
        // If only Enter is pressed, send the message
        // TODO: Send the message to the server
        setMessages([...messages, newMessage]);
        setMessage(""); // Clear the message input field
      }
    }
  };

  const chatBoxRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (chatBoxRef.current) {
      chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
    }
  }, [messages]);

  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    if (textAreaRef.current) {
      textAreaRef.current.style.height = "inherit";
      textAreaRef.current.style.height = `${textAreaRef.current.scrollHeight}px`;
    }
  }, [message]);

  return (
    <div id="chat-window">
      <div ref={chatBoxRef} id="chat-box">
        {messages.map((message, index) => {
          if (index !== messages.length - 1) {
            return (
              <div className="messageInstance" key={index}>
                <div className="messageBox">
                  <div className="messageAuthor" key={index}>
                    {message.author}
                  </div>
                  <Spacer width="10px" />
                  <div className="messageText" key={index}>
                    {message.text}
                  </div>
                </div>
                <hr />
              </div>
            );
          }
          return (
            <div className="messageInstance" key={index}>
              <div className="messageBox">
                <div className="messageAuthor" key={index}>
                  {message.author}
                </div>
                <Spacer width="10px" />
                <div className="messageText" key={index}>
                  {message.text}
                </div>
              </div>
            </div>
          );
        })}
      </div>
      <Spacer height="20px" />
      <div id="chat-input">
        <textarea
          ref={textAreaRef}
          id="message-input"
          name="message"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyDown={handleKeyDown}
        />
        <Spacer width="10px" />
        <button
          id="send-button"
          onClick={() => {
            // TODO: Send the message to the server
            // TODO: Add author to the message
            setMessages([...messages, { text: message, author: "user" }]);
            setMessage("");
          }}
        >
          Send
        </button>
      </div>
    </div>
  );
}

export default App;
