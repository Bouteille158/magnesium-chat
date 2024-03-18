interface HeaderProps {
  title: string;
}

function Header(props: HeaderProps) {
  return (
    <header className="AppHeader">
      <h2>{props.title}</h2>
    </header>
  );
}

export default Header;
