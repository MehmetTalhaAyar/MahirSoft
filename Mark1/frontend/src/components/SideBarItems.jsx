import { Link } from "react-router-dom";

export function SideBarItem(props){
    const { toWhere,icon,name } = props;
    return (
        <>
            <Link
            className="to-link"
            to={toWhere}
            style={{ textDecoration: "none" }}
            >
            <div className="sidebar_column">
                <div className="react_img">
                {icon}
                </div>

                <div className="side">{name}</div>
            </div>
            </Link>
        </>
    )
}