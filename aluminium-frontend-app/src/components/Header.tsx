import {
  askNotificationPermission,
  subscribeToPushNotifications,
} from "../services/notification";
import "./Header.scss";
import Spacer from "./Spacer";

interface HeaderProps {
  title: string;
}

function Header(props: HeaderProps) {
  return (
    <header className="AppHeader">
      <Spacer width="60px" />
      <h2
        style={{
          flex: 1,
        }}
      >
        {props.title}
      </h2>
      <div
        onClick={() => {
          askNotificationPermission().then((permission) => {
            console.log("permission status: ", permission);
            if (permission) {
              subscribeToPushNotifications();
            }
          });
        }}
        style={{
          cursor: "pointer",
          width: "40px",
          height: "40px",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <p
          style={{
            width: "100%",
            height: "100%",
            justifyContent: "center",
            alignItems: "center",
            display: "flex",
            fontSize: "40px",
          }}
        >
          🔔
        </p>
      </div>
      <Spacer width="20px" />
    </header>
  );
}

export default Header;
