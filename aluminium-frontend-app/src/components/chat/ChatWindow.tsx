import React, { useEffect, useRef, useState } from "react";
import { getMessages, postMessage } from "../../services/messages";
import { Message } from "../../types/Message";
import Spacer from "../Spacer";
import MessageInstance from "./MessageInstance";
import { useSubscription, useStompClient, IMessage } from "react-stomp-hooks";

function ChatWindow() {
  const [lastMessage, setLastMessage] = useState("No message received yet");
  useSubscription("/topic/messages", (message) =>
    handleNewWebSocketMessage(message)
  );

  const handleNewWebSocketMessage = (message: IMessage) => {
    setLastMessage(message.body);
    console.log("Received a new message from the server: ", message.body);
    getMessages().then((data) => {
      console.table(data);
      setMessages(data);
    });
  };

  const client = useStompClient();

  const sendMessageWS = (message: Message) => {
    if (client && client.connected) {
      client.publish({
        destination: "/app/send",
        body: JSON.stringify(message),
      });
    }
  };

  useEffect(() => {
    // Get messages from the server
    console.log("Getting messages from the server...");
    getMessages().then((data) => {
      console.table(data);
      setMessages(data);
    });
  }, []);

  const sendMessageToDatabase = (message: Message) => {
    // Send the message to the server
    console.log("Sending message to the server...");
    postMessage(message).then((data) => {
      console.table(data);
    });
  };

  const sendButtonHandler = () => {
    let newMessage: Message = {
      text: message,
      author: author,
      timestamp: new Date().toISOString(),
    };

    setMessages([...messages, newMessage]);
    sendMessageToDatabase(newMessage);
    sendMessageWS(newMessage);
    setMessage(""); // Clear the message input field
  };

  const [messages, setMessages] = useState<Message[]>([]);
  const [message, setMessage] = useState<string>("");
  const [author, setAuthor] = useState<string>("");

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === "Enter" && !event.shiftKey) {
      event.preventDefault(); // Prevent Enter from adding a new line

      // If message is empty, do nothing
      if (message.trim() === "") {
        return;
      }
      sendButtonHandler();
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
          return (
            <MessageInstance
              message={message}
              key={index}
              isLastMessage={index === messages.length - 1}
            />
          );
        })}
      </div>
      <div>Last Message: {lastMessage}</div>

      <Spacer height="20px" />
      <div id="chat-input">
        <input
          name="author"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
        />
        <Spacer width="10px" />
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
            sendButtonHandler();
          }}
        >
          Send
        </button>
      </div>
    </div>
  );
}

export default ChatWindow;
