import { Message } from "../../types/Message";
import Spacer from "../Spacer";
import { formatTimestamp } from "../../services/timestamp";

export interface MessageInstanceProps {
  message: Message;
  isLastMessage?: boolean;
}

function MessageInstance(props: MessageInstanceProps) {
  const messageTimestamp = formatTimestamp(props.message.timestamp);
  return (
    <div className="messageInstance">
      <div className="messageBox">
        <div className="messageAuthor">{props.message.author}</div>
        <Spacer width="10px" />
        <div className="messageText">{props.message.text}</div>
        <Spacer width="10px" />
        <div className="messageTimestamp">{messageTimestamp}</div>
      </div>
      {!props.isLastMessage && <hr />}
    </div>
  );
}

export default MessageInstance;
