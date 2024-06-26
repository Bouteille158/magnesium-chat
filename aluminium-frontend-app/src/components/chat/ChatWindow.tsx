import React, { useEffect, useRef, useState } from "react";
import { getMessages } from "../../services/messages";
import { Message } from "../../types/Message";
import Spacer from "../Spacer";
import MessageInstance from "./MessageInstance";
import { useSubscription, useStompClient, IMessage } from "react-stomp-hooks";
import Modal from "react-modal";
import { sendNotification } from "../../services/notification";
import "./ChatWindow.scss";
import { authenticate } from "../../services/authentication";

Modal.setAppElement("#root");

function ChatWindow() {
  useSubscription("/topic/messages", (message) =>
    handleNewWebSocketMessage(message)
  );

  const handleNewWebSocketMessage = (message: IMessage) => {
    console.log("Received a new message from the server: ", message.body);
    let newMessage: Message = JSON.parse(message.body);
    setMessages([...messages, newMessage]);
    sendNotification("New message !", {
      body: "You received a new message.",
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

  // Get messages from the server the first time the component is rendered
  useEffect(() => {
    console.log("Getting messages from the server...");
    getMessages().then((data) => {
      console.table(data);
      setMessages(data);
    });
  }, []);

  const sendButtonHandler = () => {
    if (message.trim() === "" || author.trim() === "") {
      return;
    }

    let newMessage: Message = {
      text: message,
      author: author,
      timestamp: new Date().toISOString(),
    };

    sendMessageWS(newMessage);
    setMessage(""); // Clear the message input field
  };

  const [messages, setMessages] = useState<Message[]>([]);
  const [message, setMessage] = useState<string>("");
  const [author, setAuthor] = useState<string>("");
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [authIsOpen, setAuthIsOpen] = useState(true);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleAuthorInput = () => {
    if (author.trim() === "") {
      return;
    }
    setModalIsOpen(false);
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === "Enter" && !event.shiftKey) {
      event.preventDefault(); // Prevent Enter from adding a new line
      sendButtonHandler();
    }
  };

  const handleAuthentication = () => {
    console.log("username: ", username);
    authenticate(username, password);

    setAuthor(username);
    setUsername("");
    setPassword("");
    setAuthIsOpen(false);
  };

  // Scroll to the bottom of the chat box when a new message is added
  const chatBoxRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    if (chatBoxRef.current) {
      chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
    }
  }, [messages]);

  // Automatically resize the message input field
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
        <Spacer height="10px" />
      </div>

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
            sendButtonHandler();
          }}
        >
          Send
        </button>
      </div>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        contentLabel="Example Modal"
        className="modal-content"
        overlayClassName="overlay"
        shouldCloseOnOverlayClick={false}
        shouldCloseOnEsc={false}
      >
        <h2>Enter your username</h2>
        <input
          name="author"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
          className="modal-input"
        />
        <Spacer width="20px" />
        <button onClick={() => handleAuthorInput()}>Validate</button>
      </Modal>

      <Modal
        isOpen={authIsOpen}
        onRequestClose={() => setAuthIsOpen(false)}
        contentLabel="Authentication modal"
        className="modal-content"
        overlayClassName="overlay"
        shouldCloseOnOverlayClick={false}
        shouldCloseOnEsc={false}
      >
        <h2>Username</h2>
        <input
          name="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <h2>Password</h2>
        <input
          name="password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Spacer width="20px" />
        <button onClick={() => handleAuthentication()}>Validate</button>
      </Modal>
    </div>
  );
}

export default ChatWindow;
