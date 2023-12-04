function NavBar() {
  return (
    <nav className="nav">
      <a href="#" className="nav_brand">
        Login Page
      </a>
      <ul className="nav_menu">
        <li className="nav_item">
          <a href="#" className="nav_link">
            Home
          </a>
        </li>
        <li className="nav_item">
          <a href="#" className="nav_link">
            About
          </a>
        </li>
        <li className="nav_item">
          <a href="#" className="nav_link">
            Skills
          </a>
        </li>
        <li className="nav_item">
          <a href="#" className="nav_link">
            Services
          </a>
        </li>
        <li className="nav_item">
          <a href="#" className="nav_link">
            Contacts
          </a>
        </li>
      </ul>
      <button className="nav_button" type="submit">
        Log-In
      </button>
    </nav>
  );
}
export default NavBar;
