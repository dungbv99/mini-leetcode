import {
  Avatar,
  Box,
  IconButton,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  TextField,
  Typography,
} from "@material-ui/core";
import { grey } from "@material-ui/core/colors";
import { makeStyles } from "@material-ui/core/styles";
import FeedbackIcon from "@material-ui/icons/Feedback";
import KeyboardBackspaceRoundedIcon from "@material-ui/icons/KeyboardBackspaceRounded";
import ReportProblemIcon from "@material-ui/icons/ReportProblemRounded";
import { AnimatePresence, motion } from "framer-motion";
import React, { useState } from "react";
import PrimaryButton from "../../component/button/PrimaryButton";
import TertiaryButton from "../../component/button/TertiaryButton";
import CustomizedDialogs from "../../component/dialog/CustomizedDialogs";
import RejectFeedbackDialog from "./RejectFeedbackDialog";

const heightVariants = {
  inactive: {
    overflowX: "hidden",
    height: 340,
    width: 550,
    transition: {
      duration: 0.2,
    },
  },
  active: {
    overflowY: "hidden",
    height: 156,
    width: 550,
    transition: {
      duration: 0.2,
    },
  },
};

const bias = 100;
const contentVariants = {
  enter: (direction) => {
    return {
      x: direction > 0 ? bias : -bias,
      opacity: 0,
    };
  },
  center: {
    zIndex: 1,
    x: 0,
    opacity: 1,
  },
  exit: (direction) => {
    return {
      zIndex: 0,
      x: direction < 0 ? bias : -bias,
      opacity: 0,
    };
  },
};

const useStyles = makeStyles((theme) => ({
  backButton: {
    width: 40,
    height: 40,
    position: "absolute",
    top: theme.spacing(1) * 1.5,
    left: theme.spacing(2),
    color: "rgba(0, 0, 0, 0.5)",
    background: grey[300],
    "&:hover": {
      background: grey[400],
    },
  },
  avatarIcon: {
    width: 60,
    height: 60,
    backgroundColor: grey[300],
    margin: "8px 12px 8px 0px",
  },
  list: {
    paddingTop: 4,
    paddingBottom: 0,
  },
  listItem: {
    borderRadius: 8,
    padding: "0 8px",
    "&:hover": {
      background: grey[100],
    },
  },
  listItemTextPrimary: {
    fontWeight: 500,
    fontSize: 17,
  },
  btn: { margin: "4px 8px" },
}));

const listItemIconStyles = { color: "black", fontSize: 26 };
const pageTitle = [
  "????ng g??p ?? ki???n cho Open ERP",
  "G??p ph???n c???i thi???n phi??n b???n Open ERP m???i",
  "???? x???y ra l???i",
];

const isEmpty = (str) => str.trim() === "";

function FeedbackDialog({ open }) {
  const classes = useStyles();

  //
  const [[page, direction], setPage] = useState([0, 0]);
  const [feature, setFeature] = useState("");
  const [detail, setDetail] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [openRejectFeedbackDialog, setOpenRejectFeedbackDialog] =
    useState(false);

  // OK
  const paginate = (newDirection) => {
    setPage([page + newDirection, newDirection]);
  };

  // OK
  const closeFeedbackDialog = () => {
    open.set(false);
    setPage([0, 0]);
    setFeature("");
    setDetail("");
  };

  // OK
  const handleClose = () => {
    if (isEmpty(feature) && isEmpty(detail)) {
      closeFeedbackDialog();
    } else {
      setOpenRejectFeedbackDialog(true);
    }
  };

  // Need upgrade
  const handleSubmit = () => {
    setIsSubmitting(true);

    setTimeout(() => {
      // gui phan hoi
      // gui xong
      closeFeedbackDialog();
      setIsSubmitting(false);
      // sau do hien thi toast
    }, 5000);
  };

  // RejectFeedbackDialog
  // OK
  const handleReject = () => {
    setOpenRejectFeedbackDialog(false);
    closeFeedbackDialog();
  };

  // OK
  const handleContinue = () => {
    setOpenRejectFeedbackDialog(false);
  };

  return (
    <>
      <CustomizedDialogs
        open={open.get()}
        handleClose={handleClose}
        contentTopDivider
        centerTitle
        title={
          page === 0 ? (
            pageTitle[0]
          ) : (
            <>
              <IconButton
                aria-label="back"
                className={classes.backButton}
                onClick={() => paginate(-page)}
              >
                <KeyboardBackspaceRoundedIcon style={{ fontSize: 28 }} />
              </IconButton>
              {pageTitle[page]}
            </>
          )
        }
        content={
          <AnimatePresence exitBeforeEnter initial={false} custom={direction}>
            <motion.div
              variants={heightVariants}
              animate={page === 0 ? "active" : "inactive"}
              initial="inactive"
            >
              <motion.div
                key={page}
                custom={direction}
                variants={contentVariants}
                initial="enter"
                animate="center"
                exit="exit"
                transition={{
                  opacity: { duration: 0.1 },
                }}
              >
                {page === 0 ? (
                  <List className={classes.list}>
                    <ListItem
                      className={classes.listItem}
                      disableGutters
                      button
                      disableRipple
                      onClick={() => paginate(1)}
                    >
                      <ListItemAvatar>
                        <Avatar className={classes.avatarIcon}>
                          <FeedbackIcon style={listItemIconStyles} />
                        </Avatar>
                      </ListItemAvatar>
                      <ListItemText
                        primary={
                          <Typography className={classes.listItemTextPrimary}>
                            G??p ph???n c???i thi???n phi??n b???n Open ERP m???i
                          </Typography>
                        }
                        secondary={
                          <Typography
                            color="textSecondary"
                            style={{ fontSize: 15 }}
                          >
                            ????ng g??p ?? ki???n v??? tr???i nghi???m v???i phi??n b???n Open
                            ERP m???i.
                          </Typography>
                        }
                      />
                    </ListItem>
                    <ListItem
                      className={classes.listItem}
                      disableGutters
                      button
                      disableRipple
                      onClick={() => paginate(2)}
                    >
                      <ListItemAvatar>
                        <Avatar className={classes.avatarIcon}>
                          <ReportProblemIcon style={listItemIconStyles} />
                        </Avatar>
                      </ListItemAvatar>
                      <ListItemText
                        primary={
                          <Typography className={classes.listItemTextPrimary}>
                            ???? x???y ra l???i
                          </Typography>
                        }
                        secondary={
                          <Typography
                            color="textSecondary"
                            style={{ fontSize: 15 }}
                          >
                            H??y cho ch??ng t??i bi???t v??? t??nh n??ng b??? l???i.
                          </Typography>
                        }
                      />
                    </ListItem>
                  </List>
                ) : (
                  <Box pt="7px" pb={1} pl={1} pr={1}>
                    <Typography
                      variant="h6"
                      component="h2"
                      style={{ margin: "0 4px" }}
                    >
                      <strong>Ch??ng t??i c?? th??? c???i thi???n nh?? th??? n??o?</strong>
                    </Typography>
                    <TextField
                      fullWidth
                      className={classes.textField}
                      label="T??nh n??ng"
                      size="large"
                      variant="outlined"
                      style={{ marginTop: 13 }}
                      value={feature}
                      onChange={(e) => setFeature(e.target.value)}
                    />

                    <Box mt={2} pt={0.5} pb={0.5}>
                      <Typography
                        component="span"
                        style={{
                          fontSize: 15,
                          fontWeight: 600,
                        }}
                      >
                        Chi ti???t
                      </Typography>
                      <TextField
                        multiline
                        fullWidth
                        rows={4}
                        variant="outlined"
                        value={detail}
                        onChange={(e) => setDetail(e.target.value)}
                        className={classes.textField}
                        id="filled-multiline-static"
                        placeholder="Vui l??ng chia s??? chi ti???t nh???t c?? th???..."
                        style={{
                          marginTop: 5,
                          backgroundColor: "rgba(0, 0, 0, 0.09)",
                        }}
                      />
                    </Box>
                    <Box
                      display="flex"
                      justifyContent="flex-end"
                      pt={2}
                      ml={-1}
                      mr={-1}
                    >
                      <TertiaryButton
                        onClick={closeFeedbackDialog}
                        className={classes.btn}
                      >
                        Hu???
                      </TertiaryButton>
                      <PrimaryButton
                        disabled={
                          isEmpty(feature) || isEmpty(detail) || isSubmitting
                        }
                        className={classes.btn}
                        style={{ width: 130 }}
                        onClick={handleSubmit}
                      >
                        {isSubmitting ? "??ang g???i..." : "G???i"}
                      </PrimaryButton>
                    </Box>
                  </Box>
                )}
              </motion.div>
            </motion.div>
          </AnimatePresence>
        }
      />

      <RejectFeedbackDialog
        open={openRejectFeedbackDialog}
        handleReject={handleReject}
        handleContinue={handleContinue}
      />
    </>
  );
}

export default FeedbackDialog;
