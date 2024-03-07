import React, { useEffect, useRef, useState } from "react";
import { getMessages } from "../../services/messages";
import { Message } from "../../types/Message";
import Spacer from "../Spacer";
import MessageInstance from "./MessageInstance";
import { useSubscription, useStompClient, IMessage } from "react-stomp-hooks";
import Modal from "react-modal";

Modal.setAppElement("#root");

function ChatWindow() {
  useSubscription("/topic/messages", (message) =>
    handleNewWebSocketMessage(message)
  );

  const handleNewWebSocketMessage = (message: IMessage) => {
    console.log("Received a new message from the server: ", message.body);
    let newMessage: Message = JSON.parse(message.body);
    setMessages([...messages, newMessage]);
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
  const [modalIsOpen, setModalIsOpen] = useState(true);

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === "Enter" && !event.shiftKey) {
      event.preventDefault(); // Prevent Enter from adding a new line
      sendButtonHandler();
    }
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
        />
        <Spacer width="20px" />
        <button onClick={() => setModalIsOpen(false)}>Validate</button>
      </Modal>
    </div>
  );
}

export default ChatWindow;
