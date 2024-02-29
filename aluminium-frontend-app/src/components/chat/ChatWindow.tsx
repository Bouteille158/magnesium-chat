import React, { useEffect, useRef, useState } from "react";
import { getMessages } from "../../services/messages";
import { Message } from "../../types/Message";
import { formatTimestamp } from "../../services/timestamp";
import Spacer from "../Spacer";

interface ChatWindowProps {
  // Définissez ici les props de votre composant
}

function ChatWindow(props: ChatWindowProps) {
  useEffect(() => {
    // Get messages from the server
    console.log("Getting messages from the server...");
    getMessages().then((data) => {
      console.table(data);
      setMessages(data);
    });
  }, []);

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
        let newMessage: Message = {
          text: message,
          author: "user",
          timestamp: new Date().toISOString(),
        };
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
          const messageTimestamp = formatTimestamp(message.timestamp);
          if (index !== messages.length - 1) {
            return (
              <div className="messageInstance" key={index}>
                <div className="messageBox">
                  <div className="messageAuthor">{message.author}</div>
                  <Spacer width="10px" />
                  <div className="messageText">{message.text}</div>
                  <Spacer width="10px" />
                  <div className="messageTimestamp">{messageTimestamp}</div>
                </div>
                <hr />
              </div>
            );
          }
          return (
            <div className="messageInstance" key={index}>
              <div className="messageBox">
                <div className="messageAuthor">{message.author}</div>
                <Spacer width="10px" />
                <div className="messageText">{message.text}</div>
                <Spacer width="10px" />
                <div className="messageTimestamp">{messageTimestamp}</div>
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
            setMessages([
              ...messages,
              {
                text: message,
                author: "user",
                timestamp: new Date().toISOString(),
              },
            ]);
            setMessage("");
          }}
        >
          Send
        </button>
      </div>
    </div>
  );
}

export default ChatWindow;
